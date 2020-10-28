package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.parser.HANLinkedList;

import java.util.ArrayList;
import java.util.HashMap;

public class EvalExpressions implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public EvalExpressions() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        apply(ast.root);
    }

    public void apply(ASTNode node){
        enterScope(0);
        checkNode(node, 0);
        exitScope(0);
    }

    public void checkNode(ASTNode node, int scope){
        ArrayList<ASTNode> recycleBin = new ArrayList<>();
        for (ASTNode child: node.getChildren()) {
            if(child instanceof VariableAssignment){
                initVariable(child, scope);
                recycleBin.add(child);
            } else if(child instanceof Expression){
                Literal literal = evaluate(child, scope);
                //Declaration does not implement the removeChild and addChild method.
                if(node instanceof Declaration){
                    ((Declaration) node).expression = literal;
                } else {
                    node.removeChild(child);
                    node.addChild(literal);
                }

            } else if(child instanceof Stylerule || child instanceof IfClause || child instanceof ElseClause){
                int newScope = scope + 1;
                enterScope(newScope);
                checkNode(child, newScope);
                exitScope(newScope);
            } else {
                checkNode(child, scope);
            }
        }
        //To prevent elements being removed from the arraylist whilst still looping over it's items they will be deleted using this function afterwards.
        TrashHandler.removeTrash(node, recycleBin);
    }

    private void exitScope(int scope) {
        variableValues.delete(scope);
    }

    private void enterScope(int scope) {
        variableValues.insert(scope, new HashMap<>());
    }

    private void initVariable(ASTNode node, int scope) {
        String name = ((VariableReference)node.getChildren().get(0)).name;
        Literal value = evaluate(node.getChildren().get(1), scope);

        node.removeChild(node.getChildren().get(0));
        node.removeChild(node.getChildren().get(1));

        int existingScope = -1;
        for (int i = scope;i>=0;i--){
            if(variableValues.get(i).get(name)!=null){
                existingScope = i;
            }
        }
        if(existingScope >= 0){
            variableValues.get(existingScope).put(name, value);
        }else {
            variableValues.get(scope).put(name, value);
        }
    }

    private Literal getVariable(ASTNode node, int scope){
        String name = ((VariableReference) node).name;
        for (int i = scope;i>=0;i--){
            if(variableValues.get(i).get(name)!=null){
                return variableValues.get(i).get(name);
            }
        }
        node.setError("Variable undefined");
        return null;
    }

    private Literal evaluate(ASTNode node, int scope) {
        if(node instanceof Literal){
            return (Literal) node;
        }else if (node instanceof VariableReference){
            return getVariable(node, scope);
        }else if (node instanceof Operation){
            if(node instanceof AddOperation){
                return evaluateAdd(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof SubtractOperation){
                return evaluateSub(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof MultiplyOperation){
                return evaluateMull(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof LargerThanOperation){
                return evaluateLarger(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof SmallerThanOperation){
                return evaluateSmaller(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof EqualsOperation){
                return evaluateEquals(evaluate(node.getChildren().get(0), scope), evaluate(node.getChildren().get(1), scope));
            } else if(node instanceof NotOperation){
                return evaluateNot(evaluate(node.getChildren().get(0), scope));
            }
        }
        return null;
    }

    private Literal evaluateNot(Literal value1) {
        BoolLiteral bool = (BoolLiteral) value1;
        bool.value = !bool.value;
        return bool;
    }

    private Literal evaluateEquals(Literal value1, Literal value2) {
        boolean result = value1.equals(value2);
        return new BoolLiteral(result);
    }

    private Literal evaluateSmaller(Literal value1, Literal value2) {
        boolean result = getIntegerValueFromLiteral(value1) < getIntegerValueFromLiteral(value2);
        return new BoolLiteral(result);
    }

    private Literal evaluateLarger(Literal value1, Literal value2) {
        boolean result = getIntegerValueFromLiteral(value1) > getIntegerValueFromLiteral(value2);
        return new BoolLiteral(result);
    }

    private Literal evaluateMull(Literal value1, Literal value2) {
        int result = getIntegerValueFromLiteral(value1) * getIntegerValueFromLiteral(value2);
        return getIntegerLiteral(value1, result);
    }

    private Literal evaluateSub(Literal value1, Literal value2) {
        int result = getIntegerValueFromLiteral(value1) - getIntegerValueFromLiteral(value2);
        return getIntegerLiteral(value1, result);
    }

    private Literal evaluateAdd(Literal value1, Literal value2) {
        int result = getIntegerValueFromLiteral(value1) + getIntegerValueFromLiteral(value2);
        return getIntegerLiteral(value1, result);
    }

    private Literal getIntegerLiteral(Literal literal, int value){
        if(literal instanceof PixelLiteral){
            return new PixelLiteral(value);
        } else if(literal instanceof PercentageLiteral){
            return new PercentageLiteral(value);
        } else {
            return new ScalarLiteral(value);
        }
    }

    private int getIntegerValueFromLiteral(Literal literal){
        if(literal instanceof PixelLiteral){
            return ((PixelLiteral) literal).value;
        }else  if(literal instanceof PercentageLiteral){
            return ((PercentageLiteral) literal).value;
        } else {
            return ((ScalarLiteral) literal).value;
        }
    }
}

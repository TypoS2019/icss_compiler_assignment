package nl.han.ica.icss.checker;

import com.google.errorprone.annotations.Var;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.parser.HANLinkedList;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        int scope = 0;
        variableTypes.addFirst(new HashMap<>());
        for (ASTNode child: ast.root.getChildren()) {
            checkASTNode(child, scope);
        }
    }

    private void checkASTNode(ASTNode node, int scope){
        if(node instanceof VariableAssignment){
            initVariable((VariableAssignment) node, scope);
        }
        if(node instanceof Declaration){
            checkDeclaration((Declaration) node, scope);
        }
        if(node instanceof Stylerule || node instanceof IfClause || node instanceof ElseClause){
            int newScope = scope+1;
            enterScope(newScope);
            if(node instanceof IfClause){
                checkIfClause((IfClause) node, newScope);
            }
            for (ASTNode child: node.getChildren()) {
                checkASTNode(child, newScope);
            }
            exitScope(newScope);
        }
    }

    private void checkDeclaration(Declaration node, int scope) {
        ExpressionType type = checkExpression(node.expression, scope);
        if(!PropertyType.get(node.property).contains(type)){
            node.setError("Invalid property type combination: " + node.property.name + ": " + type);
        }
    }

    private void enterScope(int scope){
        variableTypes.insert(scope, new HashMap<>());
    }
    private void exitScope(int scope){
        variableTypes.delete(scope);
    }

    private void initVariable(VariableAssignment node, int scope) {
        variableTypes.get(scope).put(node.name.name, checkExpression(node.expression, scope));
    }

    private ExpressionType checkExpression(Expression expression, int scope){
        ExpressionType expressionType = ExpressionType.UNDEFINED;
        if(expression instanceof Operation){
            expressionType = checkOperation((Operation) expression, scope);
        } else if (expression instanceof VariableReference){
            expressionType = checkVariableReference((VariableReference) expression, scope);
        } else if (expression instanceof Literal){
            expressionType = getExpressionLiteralType(expression);
        }
        if(expressionType == ExpressionType.UNDEFINED){
            expression.setError("Value is undefined");
        }
        return expressionType;
    }

    private void checkIfClause(IfClause node, int scope){
        checkConditionExpression(node.conditionalExpression, scope);
    }

    private void checkConditionExpression(Expression node, int scope){
        ExpressionType type = checkExpression(node, scope);
        if(type != ExpressionType.BOOL){
            node.setError("Conditional needs to be a boolean");
        }
    }

    private ExpressionType checkVariableReference(VariableReference variableReference, int scope){
        for (int i = scope; i >= 0; i--) {
            HashMap<String, ExpressionType> map = variableTypes.get(i);
            ExpressionType type = map.get(variableReference.name);
            if(type != null){
                return type;
            }
        }
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkOperation(Operation operation, int scope){
        //check if operation uses two or one child.
        if(operation.getChildren().size() == 2) {
            //Prepare the children of the operation.
            Expression child1 = (Expression) operation.getChildren().get(0);
            Expression child2 = (Expression) operation.getChildren().get(1);
            ExpressionType typeChild1 = getOperationType(scope, child1);
            ExpressionType typeChild2 = getOperationType(scope, child2);

            //Check the operation with the fitting method.
            ExpressionType type = null;
            if (operation instanceof AddOperation) {
                type = checkAddOperation(operation, typeChild1, typeChild2);
            } else if (operation instanceof SubtractOperation) {
                type = checkSubOperation(operation, typeChild1, typeChild2);
            } else if (operation instanceof MultiplyOperation) {
                type = checkMulOperation(operation, typeChild1, typeChild2);
            } else if (operation instanceof LargerThanOperation || operation instanceof SmallerThanOperation || operation instanceof EqualsOperation) {
                type = checkCompareOperation(operation, typeChild1, typeChild2);
            }
            return type;
        } else {
            //Prepare the child of the operation.
            ExpressionType type = null;
            Expression child = (Expression) operation.getChildren().get(0);
            ExpressionType typeChild = getOperationType(scope, child);
            //Check the operation with the fitting method. If more operations with one child are added in the future they can be easily added using an else if.
            if (operation instanceof NotOperation) {
                type = checkNotOperation(operation, typeChild);
            }
            return type;
        }
    }

    //check operation methods check the special requirement for each type of operation.

    private ExpressionType checkNotOperation(Operation operation, ExpressionType typeChild) {
        ExpressionType type;
        if(typeChild!=ExpressionType.BOOL){
            operation.setError("compare: Invalid type: " + typeChild + " cannot be used in combination with !");
        }
        type = getNotExpressionType(typeChild);
        return type;
    }

    private ExpressionType checkCompareOperation(Operation operation, ExpressionType typeChild1, ExpressionType typeChild2) {
        ExpressionType type;
        if (typeChild1 != typeChild2) {
            operation.setError("compare: Invalid type: " + typeChild1 + " cannot be compared with " + typeChild2);
        }
        if ((typeChild1 == ExpressionType.COLOR || typeChild2 == ExpressionType.COLOR) && !(operation instanceof EqualsOperation)) {
            operation.setError("Invalid type: Color literal cannot be used in a > or < operation");
        }
        if ((typeChild1 == ExpressionType.BOOL || typeChild2 == ExpressionType.BOOL) && !(operation instanceof EqualsOperation)) {
            operation.setError("Invalid type: boolean literal cannot be used in a > or < operation");
        }
        type = getCompareExpressionType(typeChild1, typeChild2);
        return type;
    }

    private ExpressionType checkMulOperation(Operation operation, ExpressionType typeChild1, ExpressionType typeChild2) {
        ExpressionType type;
        if (typeChild1 == ExpressionType.COLOR || typeChild2 == ExpressionType.COLOR) {
            operation.setError("Invalid type: Color literal cannot be used in an operation");
        }
        if (typeChild1 == ExpressionType.BOOL || typeChild2 == ExpressionType.BOOL) {
            operation.setError("Invalid type: Boolean literal cannot be used in an multiply operation");
        }
        if (typeChild1 != ExpressionType.SCALAR && typeChild2 != ExpressionType.SCALAR) {
            operation.setError("mul: Invalid type: " + typeChild1 + " * " + typeChild2);
        }
        type = getMulOperationType(typeChild1, typeChild2);
        return type;
    }

    private ExpressionType checkSubOperation(Operation operation, ExpressionType typeChild1, ExpressionType typeChild2) {
        ExpressionType type;
        if (typeChild1 == ExpressionType.COLOR || typeChild2 == ExpressionType.COLOR) {
            operation.setError("Invalid type: Color literal cannot be used in an operation");
        }
        if (typeChild1 == ExpressionType.BOOL || typeChild2 == ExpressionType.BOOL) {
            operation.setError("Invalid type: Boolean literal cannot be used in an subtract operation");
        }
        if (typeChild1 != typeChild2) {
            operation.setError("min: Invalid type: " + typeChild1 + " - " + typeChild2);
        }
        type = getSubOperationType(typeChild1, typeChild2);
        return type;
    }

    private ExpressionType checkAddOperation(Operation operation, ExpressionType typeChild1, ExpressionType typeChild2) {
        ExpressionType type;
        if (typeChild1 == ExpressionType.COLOR || typeChild2 == ExpressionType.COLOR) {
            operation.setError("Invalid type: Color literal cannot be used in an operation");
        }
        if (typeChild1 == ExpressionType.BOOL || typeChild2 == ExpressionType.BOOL) {
            operation.setError("Invalid type: Boolean literal cannot be used in an add operation");
        }
        if (typeChild1 != typeChild2) {
            operation.setError("add: Invalid types: " + typeChild1 + " + " + typeChild2);
        }
        type = getAddOperationType(typeChild1, typeChild2);
        return type;
    }

    private ExpressionType getOperationType(int scope, Expression node) {
        ExpressionType type = ExpressionType.UNDEFINED;
        if(node instanceof Operation){
            type = checkOperation((Operation) node, scope);
        } else if (node instanceof Literal){
            type = getExpressionLiteralType(node);
        } else if(node instanceof VariableReference){
            type = checkVariableReference((VariableReference) node, scope);
        }
        return type;
    }

    private ExpressionType getAddOperationType(ExpressionType typeChild1, ExpressionType typeChild2){
        return typeChild1;
    }
    private ExpressionType getSubOperationType(ExpressionType typeChild1, ExpressionType typeChild2){
        return typeChild1;
    }
    private ExpressionType getMulOperationType(ExpressionType typeChild1, ExpressionType typeChild2){
        if(typeChild1 != ExpressionType.SCALAR){
            return typeChild1;
        } else {
            return typeChild2;
        }
    }
    private ExpressionType getCompareExpressionType(ExpressionType typeChild1, ExpressionType typeChild2){
        return ExpressionType.BOOL;
    }
    private ExpressionType getNotExpressionType(ExpressionType typeChild){
        return ExpressionType.BOOL;
    }

    private ExpressionType getExpressionLiteralType(Expression expression){
        if(!(expression instanceof Literal)){
            return null;
        } else if(expression instanceof BoolLiteral){
            return ExpressionType.BOOL;
        }else if(expression instanceof ColorLiteral){
            return ExpressionType.COLOR;
        }else if(expression instanceof PercentageLiteral){
            return ExpressionType.PERCENTAGE;
        }else if(expression instanceof PixelLiteral){
            return ExpressionType.PIXEL;
        }else if(expression instanceof ScalarLiteral){
            return ExpressionType.SCALAR;
        }
        return null;
    }
}

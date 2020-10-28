package nl.han.ica.icss.transforms;

//BEGIN UITWERKING
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import java.util.ArrayList;
//EIND UITWERKING

public class RemoveIf implements Transform {

    @Override
    public void apply(AST ast) {
        checkNode(ast.root);
    }

    public void checkNode(ASTNode node){
        ArrayList<ASTNode> recycleBin = new ArrayList<>();
        for (int i = 0;i<node.getChildren().size();i++) {
            ASTNode child = node.getChildren().get(i);
            if(child instanceof IfClause || child instanceof Stylerule){
                checkNode(child);
            }
            if(child instanceof IfClause){
                ArrayList<ASTNode> nodes = evaluate(child);
                for (ASTNode newNode: nodes) {
                    addNodeToBody(node, newNode, i);
                    i++;
                }
                recycleBin.add(child);
            }
        }
        //To prevent elements being removed from the arraylist whilst still looping over it's items they will be deleted using this function afterwards.
        TrashHandler.removeTrash(node, recycleBin);
    }

    private void addNodeToBody(ASTNode parent, ASTNode child, int position){
        if(parent instanceof IfClause){
            ((IfClause) parent).body.add(position, child);
        } else if(parent instanceof ElseClause){
            ((ElseClause) parent).body.add(position, child);
        } else if(parent instanceof Stylerule){
            ((Stylerule) parent).body.add(position, child);
        }
    }

    private ArrayList<ASTNode> evaluate(ASTNode node) {
        ArrayList<ASTNode> nodes = node.getChildren();
        boolean condition = ((BoolLiteral) nodes.remove(0)).value;
        ElseClause elseClause = null;
        if(nodes.get(nodes.size()-1) instanceof ElseClause){
            elseClause = (ElseClause) nodes.remove(nodes.size()-1);
        }
        if(condition){
            return nodes;
        }else if(elseClause != null){
            return elseClause.body;
        }
        return new ArrayList<>();
    }
}

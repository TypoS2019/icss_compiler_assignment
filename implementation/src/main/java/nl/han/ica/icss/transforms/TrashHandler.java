package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.ElseClause;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.Stylerule;

import java.util.ArrayList;

public class TrashHandler {
    //To prevent elements being removed from the arraylist whilst still looping over it's items they will be deleted using this function afterwards.
    static void removeTrash(ASTNode node, ArrayList<ASTNode> recycleBin) {
        //Some classes do not implement the removeChild method.
        for(ASTNode trash: recycleBin){
            if(node instanceof Stylerule){
                ((Stylerule) node).body.remove(trash);
            } else if(node instanceof IfClause){
                ((IfClause) node).body.remove(trash);
            } else if(node instanceof ElseClause){
                ((ElseClause) node).body.remove(trash);
            } else {
                node.removeChild(trash);
            }
        }
    }
}

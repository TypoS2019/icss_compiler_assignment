package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

import java.util.ArrayList;

public class Generator {

	public String generate(AST ast) {
        return generate(ast.root, new StringBuilder(), -1).toString();
	}

	private StringBuilder generate(ASTNode node, StringBuilder builder, int scope){
        nodeToString(node, builder, scope);
        if(node instanceof Stylerule || node instanceof Stylesheet){
            for (ASTNode child: node.getChildren()) {
                generate(child, builder, scope + 1);
            }
        }
        nodeEndToString(node, builder, scope);
        return builder;
    }

    private StringBuilder nodeToString(ASTNode node, StringBuilder builder, int scope){
        if(node instanceof Stylerule){
            styleruleBegin(node, builder, scope);
        } else if(node instanceof Declaration){
            declarationBegin(node, builder, scope);
        }
        return builder;
    }

    private StringBuilder nodeEndToString(ASTNode node, StringBuilder builder, int scope){
        if(node instanceof Stylerule){
            styleruleEnd(node, builder, scope);
        }
        return builder;
    }


    private StringBuilder styleruleBegin(ASTNode node, StringBuilder builder, int scope){
        builder.append("  ".repeat(scope));
        ArrayList<ASTNode> body = node.getChildren();
        while(body.get(0) instanceof Selector){
            builder.append(body.remove(0)).append(" ");
            if(body.size() == 0){
                break;
            }
        }
        builder.append("{");
        builder.append("\n");
        return builder;
    }

    private StringBuilder styleruleEnd(ASTNode node, StringBuilder builder, int scope){
        builder.append("  ".repeat(scope));
        builder.append("}");
        builder.append("\n");
        return builder;
    }

    private StringBuilder declarationBegin(ASTNode node, StringBuilder builder, int scope){
        builder.append("  ".repeat(scope));
        PropertyName name = (PropertyName) node.getChildren().get(0);
        Literal value = (Literal) node.getChildren().get(1);
	    builder.append(name.name).append(": ");
	    builder.append(getLiteralString(value));
        builder.append(";\n");
	    return builder;
    }

    private String getLiteralString(Literal literal){
	    if(literal instanceof BoolLiteral){
	        if(((BoolLiteral) literal).value){
	            return "TRUE";
            }else {
	            return "FAlSE";
            }
        }else if(literal instanceof ScalarLiteral){
	        return ((ScalarLiteral) literal).value + "";
        }else if(literal instanceof PixelLiteral){
            return ((PixelLiteral) literal).value + "px";
        }else if(literal instanceof PercentageLiteral){
            return ((PercentageLiteral) literal).value + "%";
        }else if(literal instanceof ColorLiteral){
            return ((ColorLiteral) literal).value;
        }else {
	        return "";
        }
    }
}

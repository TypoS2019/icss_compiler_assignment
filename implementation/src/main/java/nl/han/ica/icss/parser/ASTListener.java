package nl.han.ica.icss.parser;

import java.util.ArrayList;
import java.util.Collections;


import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}

    public AST getAST() {
	    ast.root = (Stylesheet) currentContainer.pop();
        return ast;
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        while (currentContainer.peek() != null){
            nodes.add(currentContainer.pop());
        }
        Collections.reverse(nodes);
        ASTNode node = new Stylesheet(nodes);
        currentContainer.push(node);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        while (currentContainer.peek() != null && !(currentContainer.peek() instanceof Selector)){
            nodes.add(currentContainer.pop());
        }
        Collections.reverse(nodes);
        ArrayList<Selector> selectors = new ArrayList<>();
        while (currentContainer.peek() != null && (currentContainer.peek() instanceof Selector)){
            Selector selector = (Selector) currentContainer.pop();
            selectors.add(selector);
        }
        Collections.reverse(selectors);

        ASTNode node = new Stylerule(selectors.remove(0), nodes);
        while (selectors.size() > 0){
            node.addChild(selectors.remove(0));
        }
        currentContainer.push(node);
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        ASTNode node = new TagSelector(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ASTNode node = new ClassSelector(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        ASTNode node = new IdSelector(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode node = new Declaration();
        ASTNode expression = currentContainer.pop();
        node.addChild(currentContainer.pop());
        node.addChild(expression);
        currentContainer.push(node);
    }

    @Override
    public void exitPropertyName(ICSSParser.PropertyNameContext ctx) {
        ASTNode node = new PropertyName(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        VariableAssignment node = new VariableAssignment();
        ASTNode expression = currentContainer.pop();
        node.addChild(currentContainer.pop());
        node.addChild(expression);
        currentContainer.push(node);
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        ASTNode node = new VariableReference(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ASTNode node = new ColorLiteral(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        ASTNode node = new PercentageLiteral(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ASTNode node = new ScalarLiteral(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        ASTNode node = new PixelLiteral(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
	    ASTNode node = new BoolLiteral(ctx.getText());
        currentContainer.push(node);
    }

    @Override
    public void exitVariable(ICSSParser.VariableContext ctx) {

    }

    @Override
    public void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
	    ASTNode node = new MultiplyOperation();
        ASTNode expression = currentContainer.pop();
        node.addChild(currentContainer.pop());
        node.addChild(expression);
	    currentContainer.push(node);
    }

    @Override
    public void exitAddSubOperation(ICSSParser.AddSubOperationContext ctx) {
        ASTNode node;
        if(ctx.getChild(1).getText().equals("+")){
            node = new AddOperation();
        } else {
            node = new SubtractOperation();
        }
        ASTNode expression = currentContainer.pop();
        node.addChild(currentContainer.pop());
        node.addChild(expression);
        currentContainer.push(node);
    }

    @Override
    public void exitNotOperation(ICSSParser.NotOperationContext ctx) {
        ASTNode node = new NotOperation();
        node.addChild(currentContainer.pop());
        currentContainer.push(node);
    }

    @Override
    public void exitCompareOperation(ICSSParser.CompareOperationContext ctx) {
        ASTNode node;
        if(ctx.getChild(1).getText().equals("<")){
            node = new SmallerThanOperation();
        } else if (ctx.getChild(1).getText().equals(">")){
            node = new LargerThanOperation();
        } else {
            node = new EqualsOperation();
        }
        ASTNode expression = currentContainer.pop();
        node.addChild(currentContainer.pop());
        node.addChild(expression);
        currentContainer.push(node);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        ElseClause elseClause = null;
        if(currentContainer.peek() instanceof ElseClause){
            elseClause = (ElseClause) currentContainer.pop();
        }
        while (!(currentContainer.peek() instanceof Expression) && currentContainer.peek() != null){
            nodes.add(currentContainer.pop());
        }
        Collections.reverse(nodes);
        ASTNode node = new IfClause((Expression) currentContainer.pop(), nodes, elseClause);
        currentContainer.push(node);
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        for(int i = 0;i<ctx.getChildCount()-3;i++){
            nodes.add(currentContainer.pop());
        }
        Collections.reverse(nodes);
        ASTNode node = new ElseClause(nodes);
        currentContainer.push(node);
    }
}
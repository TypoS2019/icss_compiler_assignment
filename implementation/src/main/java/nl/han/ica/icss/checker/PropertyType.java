package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.PropertyName;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.Arrays;

public class PropertyType {
    public static ArrayList<ExpressionType> get(PropertyName name){
        ArrayList<ExpressionType> arrayList = new ArrayList<>();
        switch (name.name){
            case "width":
            case "height":
                arrayList = new ArrayList<>(Arrays.asList(ExpressionType.PIXEL, ExpressionType.PERCENTAGE));
                break;
            case "color":
            case "background-color":
                arrayList = new ArrayList<>(Arrays.asList(ExpressionType.COLOR));
                break;
        }
        return arrayList;
    }
}

package kendraslaptop.example.com.everydaytasks;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kendra's Laptop on 3/5/2017.
 */

public class FontCharacteristics {
    private Bundle extras;
    private int size;
    private int colorBlue;
    private int colorGreen;
    private int colorRed;
    private int typeStyle;
    private Typeface typeFace;

    // Font chooser keys
    private static final String SIZE = "SIZE";
    private static final String FONT_FACE = "FONT FACE";
    private static final String FONT_STYLE = "FONT STYLE";
    private static final String COLOR_RED = "COLOR RED";
    private static final String COLOR_GREEN = "COLOR GREEN";
    private static final String COLOR_BLUE = "COLOR BLUE";

    public FontCharacteristics(Bundle extras){
        this.extras = extras;
        if(this.extras != null) {
            size = (int) extras.get(SIZE);
            colorBlue = (int) extras.get(COLOR_BLUE);
            colorGreen = (int) extras.get(COLOR_GREEN);
            colorRed = (int) extras.get(COLOR_RED);
            String face = (String) extras.get(FONT_FACE);
            typeFace = getTypeFace(face);
            typeStyle = (int) extras.get(FONT_STYLE);
        }
    }

    public int getSize() {
        return size;
    }

    public int getColorBlue() {
        return colorBlue;
    }

    public int getColorGreen() {
        return colorGreen;
    }

    public int getColorRed() {
        return colorRed;
    }

    public int getTypeStyle() {
        return typeStyle;
    }

    public Typeface getTypeFace() {
        return typeFace;
    }

    private Typeface getTypeFace(String selectedType) {
        if(selectedType  == null || selectedType.isEmpty()) {
            return Typeface.DEFAULT;
        } else if("Default".equals(selectedType)) {
            return Typeface.DEFAULT;
        } else if("Bold Default".equals(selectedType)) {
            return Typeface.DEFAULT_BOLD;
        } else if("Monospace".equals(selectedType)) {
            return Typeface.MONOSPACE;
        } else if("Sans Serif".equals(selectedType)) {
            return Typeface.SANS_SERIF;
        } else if("Serif".equals(selectedType)) {
            return Typeface.SERIF;
        } else {
            return Typeface.DEFAULT;
        }
    }



}

package awt.color;

import java.awt.color.ColorSpace;

/**
 * color space label
 */
public class ColorSpaceDSC extends ColorSpace {
    /**
     * Constructs a ColorSpace object given a color space type
     * and the number of components.
     *
     * @param type          one of the <CODE>ColorSpace</CODE> type constants
     * @param numcomponents the number of components in the color space
     */
    protected ColorSpaceDSC(int type, int numcomponents) {
        super(type, numcomponents);
    }

    public float[] toRGB(float[] colorvalue) {
        return colorvalue;
    }

    public float[] fromRGB(float[] rgbvalue) {
        return rgbvalue;
    }

    public float[] toCIEXYZ(float[] colorvalue) {
        return colorvalue;
    }

    public float[] fromCIEXYZ(float[] colorvalue) {
        return colorvalue;
    }

    public static void main(String[] args){
        ColorSpaceDSC colorSpaceDSC = new ColorSpaceDSC(ColorSpace.TYPE_2CLR, 12);
        float maxValue = colorSpaceDSC.getMaxValue(1);
        System.out.println(maxValue);
    }
}


package com.xlidfwsscai525.entity;

/**
 * Class representing one entry in the chart. Might contain multiple values.
 * Might only contain a single value depending on the used constructor.
 * 
 * @author Philipp Jahoda
 */
public class Entry extends com.github.mikephil.charting.data.Entry{

    public Entry(float val, int xIndex) {
        super(val, xIndex);
    }

    public Entry(float val, int xIndex, Object data) {
        super(val, xIndex, data);
    }
}

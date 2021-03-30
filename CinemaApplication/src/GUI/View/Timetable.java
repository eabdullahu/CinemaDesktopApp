/*******************************************************************************
Enum which stores the x and y coordinates of all 12 possible slots that can be
displayed on the table.
 ******************************************************************************/
package GUI.View;
public enum Timetable {
    DATE1_SLOT1(140,85), DATE1_SLOT2(335,85), DATE1_SLOT3(530,85), DATE1_SLOT4(725,85),
    DATE2_SLOT1(140,162.5), DATE2_SLOT2(335,162.5), DATE2_SLOT3(530,162.5), DATE2_SLOT4(725,162.5),
    DATE3_SLOT1(140,240), DATE3_SLOT2(335,240), DATE3_SLOT3(530,240), DATE3_SLOT4(725,240),
    DATE4_SLOT1(140,317), DATE4_SLOT2(335,317), DATE4_SLOT3(530,317), DATE4_SLOT4(725,317);

    private int x;
    private double y;
    Timetable(int xcordinate, double ycordinate){
        x=xcordinate;
        y=ycordinate;
    }

    public int getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}

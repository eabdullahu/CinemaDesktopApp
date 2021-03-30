/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.View;

/**
 *
 * @author HP
 */
public class SlotsController {
    public int id;
    private String date;
    private String time;
    private String place;
    private short tickets;
    
    public SlotsController(int id, String date, String time, String place, short tickets) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.place = place;
        this.tickets = tickets;
    }
    
    public int getId(){
        return id;
    }
    
    public String getDate(){
        return date;
    }
    
    public String getTime(){
        switch(time){
            case "17:00:00":
                return "SLOT1";
            case "19:00:00":
                return "SLOT2";
            case "20:45:00":
                return "SLOT3";
            case "23:30:00":
                return "SLOT4";
        }
        return time;
    }
    
    public String getPlace(){
        return place;
    }
    
    public short getTickets(){
        return tickets;
    }
}

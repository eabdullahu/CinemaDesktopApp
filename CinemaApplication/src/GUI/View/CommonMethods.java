package GUI.View;

import BLL.BoughtTickets;
import BLL.Cinema;
import BLL.Consumator;
import BLL.Hall;
import BLL.Invoices;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.CinemaException;
import DAL.ConsumatorRepository;
import DAL.InvoicesRepository;
import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlLoadOptions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CommonMethods {
    public static boolean confirmed=false;      //used in loggin out
    public static boolean disablelogin=true;    //used for login btn animation
    public static String leftpanecolor="";      //used for btn drop shadow colours
    private double initialx, initialy;          //used for dragging window
    protected static String page="";            //used for keeping track of current pages
    private boolean rotatedpane =false;         //used for rotate pane animation
    public static boolean loggedout=false;
    public static boolean running = true;
    private boolean loading=false;
    private HtmlLoadOptions htmloptions = new HtmlLoadOptions();
    public static int selectedFilmId = 0;
    public static int choosenTicket = 0;
    public static Movie choosenMovie = null;
    public static MovieTickets choosenMovieTicket = null;
    public static Cinema movieCinema = null;
    public static Hall movieHall = null;
    public static Consumator consumator = null;
    public static Stage primaryStage = null;
    public static ArrayList<String> ticketsNames = new ArrayList<>();

    /*
    * Pranon bileten qe do blehet dhe konverton nga html ne pdf tiketen
    */
    public void convertToPdf(BoughtTickets bTicket, int number, double price){
        try{
            BufferedWriter fw = new BufferedWriter(new FileWriter("htmlInvoice\\index2.html"));
            fw.write(generateInvoiceHTML(bTicket, number, price));
            fw.flush();
            fw.close();
            printPDFTicket(bTicket);
        } catch(Exception io){
            io.printStackTrace();
        }
        Document doc = new Document("htmlInvoice\\index2.html", htmloptions);
        doc.save("ticketInvoices\\"+getInvoiceNumber(bTicket)+".pdf");
    }

    /*
    * Krijon invoice per bileten e blere
    */
    public void createInvoice(BoughtTickets bTicket, String generated){
        InvoicesRepository invRep = new InvoicesRepository();
        String invoiceNr = getInvoiceNumber(bTicket);
        try {
            Invoices invoice = new Invoices();
            invoice.setInvoicenumber(invoiceNr);
            invoice.setInvoicegenerate(generated);
            invoice.setInvoicebTicketid(bTicket);
            invRep.create(invoice);
        }catch(CinemaException ex){
            ex.printStackTrace();
        }
    }

    /*
    * Gjeneron numrin per invoice
    */
    public String getInvoiceNumber(BoughtTickets bTicket){
        Date today = new Date();
        return "E"+consumator.getClientid()+"_"+new SimpleDateFormat("yyyyMMdd").format(today)+"_"+bTicket.getBTicketrow()+bTicket.getBTicketseatnumber();
    }

    /*
    * Merr html kodin edhe e kthen ne pdf me parametrat e marr nga tiketa
    */
    public String generateInvoiceHTML(BoughtTickets bTicket, int number, double price){
        String toReturn = "<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">"+consumator.getClientname()+" "+consumator.getClientsurname()+"</h2>\n<div class=\"address\">"+consumator.getClientaddres()+"</div>\n<div class=\"email\"><a>"+consumator.getClientemail()+"</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE "+getInvoiceNumber(bTicket)+"</h1>\n<div class=\"date\">Date of Invoice: "+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+"</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>"+choosenMovie.getMoviename()+"</h3></td>\n<td class=\"unit\">"+choosenMovieTicket.getTicketprice()+"</td>\n<td class=\"qty\">"+number+"</td>\n<td class=\"total\">"+price+"&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>"+price+"&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>"+price+"&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can't get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>";
        createInvoice(bTicket, toReturn);
        return toReturn;
    }

    /*
    * HTML kodi parametrat i vendos ne html ne baze te tiketes edhe e bene konvertimin e ati gtml ne pdf
    */
    public void printPDFTicket (BoughtTickets bTicket){
        Hall hall = choosenMovieTicket.getTickethallid();
        String htmlCode = "<html>\n<head>\n<title>Ticket receipt</title>\n</head>\n<style>\nbody {\nfont-family: sans-serif; \n display: flex;\nwidth: 100%;\nheight: 100%;\njustify-content: center;\nalign-items: center}\n.receiptContainer {\nwidth:478px;\nheight: 150px;\nbackground-color: #FFF;\npadding: 10px; \nborder:1px solid rgba(71,185,234, 0.3);\n}\n.receiptContainer #first {\ndisplay:inline-block; \nwidth: 150px;\n}\n.receiptContainer #first h5{\nfont-weight: 700; \nmargin: 0; \ntext-align: center;\n}\n\n.receiptContainer #second {\ndisplay:inline-block;\nwidth: 320px; \nheight:100%; \nborder-left: 3px dashed #ccc;\n}\n.receiptContainer #second h3 {\nfont-weight: 600; \nmargin: 0; \ncolor: #FFF; \nmargin-left: 10px;\ntext-align: center;\nbackground:#47b9ea;\npadding: 5px 0;\n}\n\ntable {\nborder-collapse: separate;\nfont-size: 14px;\npadding: 10px;\nwidth: 100%;\n}\ntable tr .td1{\nfont-weight: 600;\nopacity: 0.9;\npadding-left: 10px;\n}\ntable tr .td2{\ncolor: #47b9ea;\nfont-weight: 700;\ntext-align: right;\npadding-left: 0;\n}\ntable tr .td1new{ \nfont-weight: 500;\nfont-size: 12px;\ntext-align: center;\n}\n</style>\n<body>\n<div class=\"receiptContainer\">\n<div id=\"first\">\n<h5>ONLINE TICKET</h5>\n<table>\n<tr>\n<td class=\"td2\">ROW</td>\n<td class=\"td1\">"+ (char)(64+bTicket.getBTicketrow()) +"</td>\n</tr>\n<tr>\n<td class=\"td2\">SEAT</td>\n<td class=\"td1\">"+bTicket.getBTicketseatnumber()+"</td>\n</tr>\n<tr>\n<td class=\"td2\">DATE</td>\n<td class=\"td1\">"+new SimpleDateFormat("dd-MM-yyyy").format(choosenMovieTicket.getTicketplaydate())+"</td>\n</tr>\n<tr>\n<td class=\"td2\">TIME</td>\n<td class=\"td1\">"+new SimpleDateFormat("HH:mm:ss").format(choosenMovieTicket.getTicketplayhour())+"</td>\n</tr>\n<tr>\n<td class=\"td2\">PRICE</td>\n<td class=\"td1\">"+choosenMovieTicket.getTicketprice()+"&euro;</td>\n</tr>\n</table>\n</div>\n<div id=\"second\">\n<h3>&#10026; MOVIE NAME</h3>\n<table>\n<tr>\n<td class=\"td2\">ROW</td>\n<td class=\"td1\">"+ (char)(64+bTicket.getBTicketrow()) +"</td>\n<td class=\"td2\">SEAT</td>\n<td class=\"td1\">"+bTicket.getBTicketseatnumber()+"</td>\n</tr>\n<tr>\n<td class=\"td2\">DATE</td>\n<td class=\"td1\">"+new SimpleDateFormat("dd-MM-yyyy").format(choosenMovieTicket.getTicketplaydate())+"</td>\n<td class=\"td2\">TIME</td>\n<td class=\"td1\">"+new SimpleDateFormat("HH:mm:ss").format(choosenMovieTicket.getTicketplayhour())+"</td>\n</tr>\n<tr>\n<td class=\"td2\">Hall</td>\n<td class=\"td1\">"+ hall.getHallname() +"</td>\n<td class=\"td2\">PRICE</td>\n<td class=\"td1\">"+choosenMovieTicket.getTicketprice()+"&euro;</td>\n</tr>\n<tr>\n<td class=\"td1 td1new\" colspan=\"4\">"+ getInvoiceNumber(bTicket) +"</td>\n</tr>\n</table>\n</div>\n</div>\n</body>\n</html>";
        try {
            FileWriter fw = new FileWriter("htmlInvoice\\ticket.html");
            fw.write(htmlCode);
            fw.flush();
            Document doc = new Document("htmlInvoice\\ticket.html", htmloptions);
            String fileName = bTicket.getBTicketid()+""+bTicket.getBTicketrow()+""+bTicket.getBTicketseatnumber()+"";
            doc.save("ticketInvoices\\"+fileName+".pdf");
        }catch(Exception io){
            io.printStackTrace();
        }
    }

    /*
    * Dergon email me attachments, ne kete rast eshte perdor
    * per me i dergu tiketat ne email te useri qe i ka ble ku i lexon prej nje liste statike
    */
    public void sendEmailWithAttachments(String sendTo)  {
        String host = "smtp.gmail.com";
        String port = "587";
        String userName = "email@gmail.com";
        String password = "test.";
 
        String toAddress = sendTo;
        String subject = "Movie booking system";
        String message = "Your order was successful. We have attached your tickets for the movie.";
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (ticketsNames != null && ticketsNames.size() > 0) {
                for (String filePath : ticketsNames) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile("ticketInvoices\\"+filePath+".pdf");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    multipart.addBodyPart(attachPart);
                }
            }
            msg.setContent(multipart);
            Transport.send(msg);
        }catch(Exception io){
            System.out.println("Couldn't send email.");
        }
    }

    /*
    * Dergon email te thjeshte qe njofton userin per diqka
    */
    public void sendNewEmail(String sendTo, String from, String subject, String txtmsg){
        final String username = from;
        final String password = "......";
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
           }
        });

        try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(username));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
           message.setSubject(subject);
           message.setText(txtmsg);
           Transport.send(message);
        } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
    }

    /*
    * e mbyll window nga eventi i krijuar
    */
    public void handleClose(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /*
    * Nese useri eshte i kycur Login buttoni ndrrohet me Logout, perndryshe rrin login
    */
    public void returnAction(JFXButton logoutbtn){
        logoutbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(consumator == null) {
                    logIn(e);
                }else {
                    logOut(e);
                }
            }
        });
    }

    /*
    * Label se kush eshte i kycur me cfar accounti e display emrin & mbiemrin
    */
    public void displayUserName(Label label){
        if(consumator == null){
            label.setVisible(false);
        }else{
            label.setText("Logged in as: "+consumator.getClientname()+" "+consumator.getClientsurname());
        }
    }

    /*
    * E hap skenen/page, ne baze te butonit qe eshte kliku ne anen e majt apo tek menyja
    * Cilen sken/page me hap nvaret nga id e butonit qe e kemi vendos ne switch
    */
    public  void loadScene(ActionEvent event){
        if(loading){
            return;
        }
        loading=true;
        String newscene="";
        CommonMethods.confirmed=false;
        if(((JFXButton)(event.getSource())).getId().equals("homebtn")){
            newscene="home.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("contactbtn")){
            newscene="contact.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("cinemabtn")){
            newscene="addCinema.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("hallsbtn")){
            newscene="addHall.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("moviesbtn")){
            newscene="addMovies.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("ticketsbtn")){
            newscene="addTicket.fxml";
        }else if(((JFXButton)(event.getSource())).getId().equals("usersbtn")){
            newscene="addConsumator.fxml";
        }
        
        Timeline t=dropShadow(event, 0.75,leftpanecolor);
        t.play();
        String newFinalScene = newscene;
        t.setOnFinished(event1 -> {
            try{
                launchScene(newFinalScene);
            }catch(Exception o){
                o.printStackTrace();
            }
        });
    }

    /*
    * Rrotullon nje button 45 shkalle ne drejtim te ores pastaj e kthen ne vendin e kaluar me transition
    */
    public void rotateButton(JFXButton btn){
        if(rotatedpane ==false){
            rotatedpane =true;
            RotateTransition rt=new RotateTransition(Duration.millis(60),btn);
            rt.setByAngle(45);
            rt.setCycleCount(2);
            rt.setAutoReverse(true);
            rt.play();

            rt.setOnFinished(event -> {
                RotateTransition rt2=new RotateTransition(Duration.millis(60),btn);
                rt2.setByAngle(-45);
                rt2.setCycleCount(2);
                rt2.setAutoReverse(true);
                rt2.play();
                rt2.setOnFinished(event1 -> rotatedpane =false);
            });
        }
    }

    /*
    * Perdoret per me vendos shadow effect te ni buton kur klikohet
    * Ngjyra e shadow nvaret prej ngjyres ne anen e majte
    */
    public Timeline dropShadow(ActionEvent event, double shadowspread, String color){
        JFXButton btn=(JFXButton) event.getSource();
        Color origcolor=Color.web(color);
        Color newcolor=origcolor.brighter();
        String darker=("#"+newcolor.toString().substring(2,8));
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(darker));
        shadow.setSpread(shadowspread);

        Timeline shadowAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 0d)),
                new KeyFrame(Duration.millis(150), new KeyValue(shadow.radiusProperty(), 20d)));
        shadowAnimation.setAutoReverse(true);
        shadowAnimation.setCycleCount(2);
        Node target = btn;
        target.setEffect(shadow);
        return shadowAnimation;
    }

    /**
    *  Creates a hover effect on a anchorpane, the background color
    *  of the pane is retrieved and made darker and this new darker
    *  color is then set as the background color.
    */
    public void tileHover(MouseEvent event){
        AnchorPane pane=(AnchorPane)event.getSource();
        String original=pane.getStyle().substring(22,29);
        Color origcolor=Color.web(original);

        Color newcolor=origcolor.darker();
        String darker=("#"+newcolor.toString().substring(2,8));
        pane.setStyle("-fx-background-color: "+darker);
        pane.setEffect(new Bloom(0.85));
    }

    /**
    *  A reverse of the tileHover method, simply gets the current color
    *  of the anchorpane and creates a new brighter color which is then
    *  assigned as the new color for the pane.
    */
    public void tileExit(MouseEvent event){
        AnchorPane pane=(AnchorPane)event.getSource();
        String original=pane.getStyle().substring(22,29);
        Color origcolor=Color.web(original);

        Color newcolor=origcolor.brighter();
        String brighter=("#"+newcolor.toString().substring(2,8));
        pane.setStyle("-fx-background-color: "+brighter);
        pane.setEffect(new Bloom(1));
    }

    /**
    *  Similar to the tileHover method, changes the colour of a buttons
    *  background and its opacity. Hovering over a button gives it a dark
    *  background and 100% opacity.
    */
    public void btnHover(MouseEvent event){
        JFXButton btn=(JFXButton) event.getSource();
        Color origcolor=Color.web(leftpanecolor);
        Color newcolor=origcolor.darker();

        btn.setOpacity(1.0);
        String darker=("#"+newcolor.toString().substring(2,8));
        btn.setStyle("-fx-background-color: "+darker);
        btn.setEffect(new Bloom(0.75));
    }

    /**
    *  Reverse of the btnHover method, when the mouse exits the buttons dimensions,
    *  the background changes to transparent and the opacity to 80%.
    */
    public void btnExit(MouseEvent event){
        JFXButton btn=(JFXButton) event.getSource();
        if((btn.getText().equals(page))) {
            btn.setOpacity(1.0);
        }else{
            btn.setOpacity(0.8);
        }
        btn.setStyle("-fx-background-color: transparent");
        btn.setEffect(new Bloom(1));
    }

    /**
    *  Adds a basic hover effect to a buttton, darkens the background
    *  color on hover and sets it back to original on exit.
    */
    public void btnEffect(JFXButton btn){
        String btncolor=btn.getStyle().substring(22,29);
        Color origcolor=Color.web(btncolor);
        Color newcolor=origcolor.darker();
        String darker=("#"+newcolor.toString().substring(2,8));

        btn.setOnMouseEntered(event -> {
            btn.setStyle("-fx-background-color: "+darker);
            btn.setEffect(new Bloom(0.85));
        });
        String brighter=("#"+origcolor.toString().substring(2,8));
        btn.setOnMouseExited(event -> {
            btn.setStyle("-fx-background-color: "+brighter);
            btn.setEffect(new Bloom(1));
        });
    }

    /**
    *  Method to solve small bug which is when the window or a new page gets
    *  loaded, some of the buttons visual focus is set to true and have grey
    *  backgrounds even though the mouse is not hovering over them. This simply
    *  disables all of the visualFocus on the buttons in a page.
    */
    public void disableAllFocus(Pane pane){
        for (Node n: pane.getChildren()){
            if(n instanceof JFXButton){
                ((JFXButton) n).setDisableVisualFocus(true);
            }else if((n instanceof AnchorPane) || (n instanceof HBox)){
                disableAllFocus((Pane) n);
            }
        }
    }

    /**
    *  Loads a new popup window asking the user to confirm
    *  whether or not they want to logout from application.
    */
    public void logOut(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        AnchorPane mainpane= (AnchorPane) ((Node) event.getSource()).getParent().getParent();
        mainpane.setEffect(new ColorAdjust(0,0,-0.25,0));
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("logoutpop.fxml"));
            Stage popup = new Stage();
            Scene scene=new Scene(parent);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            popup.initOwner(stage);
            popup.setScene(scene);
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();
            if(confirmed==true){
                stage.close();
                try {
                    loggedout = true;
                    consumator = null;
                    launchScene("home.fxml");
                } catch (IOException ex) {
                    System.out.println("Error in switching stages");
                }
            }else{
                disableAllFocus(mainpane);
                mainpane.setEffect(new ColorAdjust(0,0,0,0));
            }
        } catch (IOException ex) {
            System.out.println("Error in switching stages logout btn");
            ex.printStackTrace();
        }
    }

    /**
    *  If the user confirmed in the the previous popup window
    *  for confirmation, application is closed and the initial
    *  login window is loaded and displayed. Is declared private
    *  since its only used by the logOut method.
    */
    public void logIn(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        AnchorPane mainpane = (AnchorPane) ((Node) event.getSource()).getParent().getParent();
        mainpane.setEffect(new ColorAdjust(0,0,-0.25,0));
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage popup = new Stage();
            Scene scene=new Scene(parent);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            popup.initOwner(stage);
            popup.setScene(scene);
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();
            if(consumator == null){
                disableAllFocus(mainpane);
                mainpane.setEffect(new ColorAdjust(0,0,0,0));
            }else{
                stage.close();
                try {
                    launchScene("home.fxml");
                } catch (IOException ex) {
                    System.out.println("Error in switching stages");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error in switching stages logout btn");
            ex.printStackTrace();
        }
    }

    /**
    *  Given a pane as an argument, sets properties of the pane
    *  to allow the user to move the window around by clicking
    *  and holding on the pane which will move the window according
    *  to the position of mouse.
    */
    public void moveWindow(AnchorPane pane){
        pane.setOnMousePressed(e ->{
            initialx = e.getSceneX();
            initialy = e.getSceneY();
        });
        pane.setOnMouseDragged(e -> {
            Node source = (Node) e.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setX(e.getScreenX() - initialx);
            stage.setY(e.getScreenY() - initialy);
        });
    }

    /**
    *  Provides the ability to minimise the window, which window/stage
    *  is minimised is determined from the source of the button that
    *  triggered this even to occur.
    */
    public void minimiseWindow(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
    *  Creates and plays a scale transition on the Anchorpane passed to it.
    *  Decreases the size of the pane to 50% and plays the animation upto
    *  its original size.
    */
    public void popNode(AnchorPane pane){
        ScaleTransition st = new ScaleTransition(Duration.millis(800), pane);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
    }

    /**
    *  Creates and plays a scale transition on the Scrollpane passed to it.
    *  Decreases the size of the pane to 50% and plays the animation upto
    *  its original size.
    */
    public void popNodeNew(ScrollPane pane){
        ScaleTransition st = new ScaleTransition(Duration.millis(800), pane);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
    }

    public SequentialTransition makeBtnFly(JFXButton btn){
        TranslateTransition t1=new TranslateTransition(Duration.millis(200),btn);
        t1.setToY(-17d);
        PauseTransition p1=new PauseTransition(Duration.millis(30));
        TranslateTransition t2=new TranslateTransition(Duration.millis(200),btn);
        t2.setToY(0d);

        SequentialTransition transition=new SequentialTransition(btn, t1,p1,t2);
        return transition;
    }
    
     public void popButton(JFXButton btn){
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
        st.setOnFinished(event -> {
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), btn);
            st2.setToX(1);
            st2.setToY(1);
            st2.setRate(1.5);
            st2.setCycleCount(1);
            st2.play();
        });
    }

    /**
     * Ndrron skenen ne baze te file fxml name qe kemi dergu si parameter
     */
    public void launchScene(String sceneName) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            CommonMethods.primaryStage.setScene(scene);
            CommonMethods.primaryStage.show();
        } catch (IOException ex) {
            System.out.println("Error in switching stages");
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Shikon nese email eshte valide
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\." +
                "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Modal qe hapet ne rast se useri/admini deshiron me ndryshu profile
     */
    public void editProfile(StackPane pane, AnchorPane mainPane) {
        Text title = new Text("Edit profile");
        title.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 13));
        
        JFXTextField nameField = new JFXTextField();
        nameField.setPromptText("Name");
        nameField.setLabelFloat(true);
        nameField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        nameField.setText(consumator.getClientname());
        
        JFXTextField lastnameField = new JFXTextField();
        lastnameField.setPromptText("Lastname");
        lastnameField.setLabelFloat(true);
        lastnameField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        lastnameField.setText(consumator.getClientsurname());
        
        JFXTextField emailField = new JFXTextField();
        emailField.setPromptText("Email");
        emailField.setLabelFloat(true);
        emailField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        emailField.setText(consumator.getClientemail());
        
        JFXTextField addressField = new JFXTextField();
        addressField.setPromptText("Address");
        addressField.setLabelFloat(true);
        addressField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        addressField.setText(consumator.getClientaddres());
        
        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setPromptText("Password");
        passwordField.setLabelFloat(true);
        passwordField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        
        Label label = new Label();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(nameField, lastnameField, emailField, addressField, passwordField, label);
        
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(title);
        dialogContent.setPrefWidth(280);
        dialogContent.setBody(vbox);
        
        JFXButton update = new JFXButton("Update");
        update.setButtonType(JFXButton.ButtonType.RAISED);
        update.setStyle("-fx-background-color: #00b59c; -fx-text-fill: white");
        update.setOnAction(event -> {
            popButton(update);
            userEdit(nameField, lastnameField, emailField, addressField, passwordField, label);
        });
        
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #FF9A00; -fx-text-fill: white");
        
        dialogContent.setActions(update, close);
        JFXDialog dialog = new JFXDialog(pane, dialogContent, JFXDialog.DialogTransition.TOP);
        dialog.setOverlayClose(false);
        close.setOnAction(event -> {
            popButton(close);
            dialog.close();
        });
        dialog.show();
    }

    /**
     * Kjo metode validon ato te dhana qe jane shtyp / fields dhe nese gjithqka nrregull e ben update profilin e userit/adminit
     */
    public boolean userEdit(JFXTextField name, JFXTextField surename, JFXTextField email, JFXTextField address, JFXPasswordField password, Label label){
        ConsumatorRepository cRep = new ConsumatorRepository();
        if(!(isValidEmailAddress(email.getText()))) {
            label.setText("Invalid email address.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        } else {
            if(!consumator.getClientemail().equals(email.getText())){
                try {
                    Consumator c = cRep.findByClientEmail(email.getText());
                    if(c != null){
                        label.setText("Email already taken.");
                        label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
                        return false;
                    }
                }catch(CinemaException io){
                    io.printStackTrace();
                }
            }
        }
        if(address.getText() == null || address.getText().isEmpty()){
            label.setText("Address can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        if(name.getText() == null || name.getText().isEmpty()){
            label.setText("Name can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        if(surename.getText() == null || surename.getText().isEmpty()){
            label.setText("Lastname can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        if(!password.getText().isEmpty() && password.getText().length() < 8){
            label.setText("Password min length is 8.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        try{
            consumator.setClientname(name.getText());
            consumator.setClientsurname(surename.getText());
            consumator.setClientemail(email.getText());
            consumator.setClientaddres(address.getText());
            if(!password.getText().isEmpty()){
                consumator.setClientpassword(password.getText());
            }
            cRep.edit(consumator);
        }catch(CinemaException io){
            label.setText("Couln't register, try again.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;"); 
            return false;
        }
        label.setText("Account updated successfully.");
        label.setStyle("-fx-text-fill: #68AE00; -fx-font-size: 12px; -fx-padding: 10 0 0 0;"); 
        return true;
    }

    /**
     * Kthen nje random string qe eshte perdor tek passwordi, length i string nvaret nga parametri
     */
    public String getRandomString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index)); 
        }
        return sb.toString(); 
    }
    
    /*
    * New check if the String is number
    * used to check/validate admin input
    */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer d =Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /*
     * New check if the String is number
     * used to check/validate admin input
     */
    public boolean deletePopup(){
        // Makes the alert popup.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this?", ButtonType.YES, ButtonType.NO);
        // clicking X also means no
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        return ButtonType.YES.equals(result);
    }
}
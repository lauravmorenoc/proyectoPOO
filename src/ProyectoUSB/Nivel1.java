/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoUSB;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 *
 * @author Juan
 */
public class Nivel1 extends Vista {
   // private Scene scene;
    private GraphicsContext gc;
    private StackPane stackGrid;
    private Image background1;
    private ImageView image1;
    private Button pause;
    private Canvas canvas;
    private HBox hbox;
    private int cont=650;
    private boolean salto;
    private int contadorSalto;
    private boolean objectUp;
    private boolean objectDown;
    protected boolean dangerDown;
    private Modelo modelo;

    public Nivel1(Modelo modelo){
        this.modelo = modelo;
        objectUp=false;
        objectDown=true;
        dangerDown=false;
        contadorSalto=0;
        salto=false;
        stackGrid = new StackPane();
        background1 = new Image("Imagenes/esc1.jpg");
        image1 = new ImageView(background1);
        //background2 = new Image("Imagenes/ground.jpg");
        //image2 = new ImageView(background2);


        pause = new Button ("Pause");
        pause.setAlignment(Pos.TOP_RIGHT);


        hbox = new HBox();
        hbox.setAlignment(Pos.TOP_RIGHT);
        hbox.getChildren().add(pause);

        layout.getChildren().add(hbox);
        //scene = new Scene (stackGrid, 650, 406);


        this.player = new Player(300, 331, 29, 39, 20, new Image("Imagenes/down1.png"));
        this.floor= new StaticObject(0, 371, 300, 200, new Image("Imagenes/floor1.jpg"));
        this.bg=new Background(0, 0, 650, 406, new Image("Imagenes/esc1.jpg"));
        this.bgInverted=new Background(0, 0, 650, 406, new Image("Imagenes/esc1.jpg"));

        StaticObject bloque0=new StaticObject(495, 331, 59, 40, new Image("Imagenes/alienfloor2_diffuse.jpg")); //Los terminos x, y deben ser el x, y
        this.objects.add(bloque0);                                                                              //del jugador + o - multiplos de quince para que las colisiones se registren 
                                                                                                                    //Width y height deben ser multipos de 15 menos una unidad
        StaticObject bloque1=new StaticObject(990, 331, 74, 14, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque1);
        
        StaticObject bloque2=new StaticObject(1470, 331, 59, 40, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque2);
        
        StaticObject bloque3=new StaticObject(1530, 301, 59, 70, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque3);
        
        StaticObject bloque4=new StaticObject(1590, 271, 59, 100, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque4);
        
        StaticObject bloque5=new StaticObject(1830, 331, 29, 40, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque5);
        
        StaticObject bloque6=new StaticObject(1860, 301, 29, 70, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque6);
        
        StaticObject bloque7=new StaticObject(1980, 301, 29, 70, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque7);
        
        StaticObject bloque8=new StaticObject(2010, 331, 29, 40, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque8);

        StaticObject bloque9=new StaticObject(2505, 316, 74, 14, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque9);
        
        StaticObject bloque10=new StaticObject(3465, 331, 59, 14, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque10);
        
        StaticObject bloque11=new StaticObject(3525, 301, 59, 14, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque11);

        StaticObject bloque12=new StaticObject(3585, 271, 59, 14, new Image("Imagenes/alienfloor2_diffuse.jpg"));
        this.objects.add(bloque12);
        
        SpecialFloor water1= new SpecialFloor(980, 104);
        this.objects.add(water1);
        
        SpecialFloor water2= new SpecialFloor(1650, 59);
        this.objects.add(water2);
        
        SpecialFloor water3= new SpecialFloor(1890, 89);
        this.objects.add(water3);
        
        SpecialFloor water4= new SpecialFloor(2490, 104);
        this.objects.add(water4);
        
        SpecialFloor water5= new SpecialFloor(2850, 89);
        this.objects.add(water5);
        
        SpecialFloor water6= new SpecialFloor(3450, 219);
        this.objects.add(water6);
    }
    public void show(Stage stage) {
      stage.setTitle("Orion's Maze");
      stage.setScene(scene);
      this.start();
      stage.show();
   }

    public boolean getSalto() {
        return salto;
    }

    public void setSalto(boolean salto) {
        this.salto = salto;
    }


    public void mostrar(Stage stage){

        stage.setTitle("Ventana 1");
        this.start();
        stage.setScene(this.scene);

        stage.show();
    }  

    public Button getPause() {
        return pause;
    }

    public void toJump(){
        this.player.start();
        salto=false;
    }

    @Override
    public void handle(long now){

        dangerDown=false;
        Shape playerHitbox = new Rectangle(player.getxPos(), player.getyPos(), 29, 39);

        if(frames % 2 == 0){

            pencil.clearRect(0, 0, 650, 406);

            if(enemigos.isEmpty()){
                Enemy enemigo = new Enemy(this.floor.getxPos()+600, 324, 40, 40, new Image("Imagenes/left0.png"));
                enemigos.add(enemigo);
            }

            for(int n=0; n<20; n++){
                pencil.drawImage(floor.getSprite(), floor.getxPos()+(300*(n-1)), floor.getyPos(), floor.getWidth(), floor.getHeight());
                if(n%2==0){
                pencil.drawImage(bg.getSprite(), bg.getxPos()+(648*n), bg.getyPos(), bg.getWidth(), bg.getHeight());
              } else pencil.drawImage(bgInverted.getSprite(), bgInverted.getxPos()+(648*n), bgInverted.getyPos(), bgInverted.getWidth(), bgInverted.getHeight());
            }


            for(StaticObject object:this.objects){
                if(object.getxPos()+object.getWidth()>=0)
                pencil.drawImage(object.getSprite(), object.getxPos(), object.getyPos(), object.getWidth(), object.getHeight());
            }

            for(Enemy enemy : this.enemigos){
                if(!enemigos.isEmpty()){
                    Shape enemyHitbox = new Rectangle (enemy.getxPos(), enemy.getyPos(), enemy.getWidth(), enemy.getHeight());
                    Shape comparator = SVGPath.intersect(enemyHitbox, playerHitbox);
                    pencil.drawImage(enemy.getSprite(), enemy.getxPos(), enemy.getyPos());
                    enemy.start();
                    if(comparator.getBoundsInLocal().getWidth() != -1){
                        System.out.println("Colision");
                        if(modelo.getSalud() > 0){
                            this.modelo.setSalud(this.modelo.getSalud() - 1);
                            for(Enemy enemigo : enemigos){
                                enemigo.setxPos(enemigo.getxPos() + 15);
                            }
                            for(StaticObject o : objects){
                                o.setxPos(o.getxPos() + 15);
                            }
                            bg.setxPos(bg.getxPos() + 15);
                            bgInverted.setxPos(bgInverted.getxPos() + 15 );
                            floor.setxPos(floor.getxPos() + 15);
                        }
                        int vidasActuales = modelo.getVidas();
                        if(modelo.getSalud() == 0 && modelo.getVidas() != 0){
                            modelo.setVidas(modelo.getVidas() -1);
                            modelo.setSalud(5);
                        }

                        if(vidasActuales != modelo.getVidas()){
                            vidasActuales = modelo.getVidas();
                            modelo.setVidas(vidasActuales);
                            for(Enemy enemigo : enemigos){
                                enemigo.setxPos(enemigo.xPosInicial);
                            }
                            for(StaticObject o : objects){
                                o.setxPos(o.xPosInicial);
                            }
                            bg.setxPos(bg.xPosInicial);
                            bgInverted.setxPos(bgInverted.xPosInicial);
                            floor.setxPos(floor.xPosInicial);
                        }
                        if(modelo.getVidas() == 0){
                            this.stop();
                        }
                    }
                }
            }

            pencil.drawImage(player.getSprite(), player.getxPos(), player.getyPos());
            pencil.strokeRect(player.getxPos(), player.getyPos(), 29, 39);


            //Aqui mira si hay objetos arriba o abajo del personaje
            boolean atLeastOneDown=false;
            boolean atLeastOneUp=false;
            
            for(StaticObject objeto:this.objects){

               boolean cond1=player.getxPos()<(objeto.getxPos()+objeto.getWidth())&&(player.getxPos()+player.getWidth())>objeto.getxPos();

               if(((player.getyPos()-1)==(objeto.getyPos()+objeto.getHeight()))&&cond1){                     
                 objectUp=true;
                 atLeastOneUp=true;
               } else {
                   if(!atLeastOneUp){
                        objectUp=false;
                   }
               }

               if(((player.getyPos()+player.getHeight()+1)==(objeto.getyPos()))&&cond1){
                 objectDown=true;
                 atLeastOneDown=true;
                 
                 if(objeto.getID()==2&&player.getxPos()>=objeto.getxPos()&&(player.getxPos()+player.getWidth())<=(objeto.getxPos()+objeto.getWidth())){
                     dangerDown=true; //Este es el boolean del agua
                     System.out.println("This happens");
                     atLeastOneDown=false;
                     objectDown=false;
                 }
                 
               } else{
                   if(!atLeastOneDown){
                       objectDown=false;
                   }
                 
               }
            }

            //Aqui mira exclusivamente si no tiene piso debajo
               if(((player.getyPos()+player.getHeight()+1)==(floor.getyPos()))&&!dangerDown){
                 objectDown=true;
               }



            if(salto&&contadorSalto<=100&&!objectUp){

                player.moveUp();
                contadorSalto++;

            }else if(!objectDown){
                //contadorSalto>0&&
                player.moveDown();
                contadorSalto--;
                salto=false;
            }
            }



        frames++;
        if(frames == 5) frames = 0;
    }


}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;

/**
 *
 * @author Usuario
 */

public class GameFlow {

    public static ArrayList<Card> cards = new ArrayList<Card>();
    public static ArrayList<Color> colors = new ArrayList<Color>();
    public static ArrayList<Color> colorPallete = new ArrayList<>(Arrays.asList(Color.red, Color.blue, Color.green, Color.yellow, Color.BLACK, Color.CYAN, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK));
    
    public static int lifes = 3;
    public static int actualLife=0;
    public static JLabel labelLife;
    public static int score=0;
    
    public static int cardsTurned = 0;
    public static Card aux_Card;
    
    public static void Init(int cardNumber)
    {
        createColors(cardNumber);
        
        actualLife=lifes;
        cardsTurned=0;
        score=0;
        aux_Card=null;
        labelLife.setText(""+lifes);
    }
    
    public static void createColors(int cardNumber)
    {
        Collections.shuffle(colorPallete);
        
        for (int i = 0; i < cardNumber/2; i++) {
            colors.add(colorPallete.get(i));
            colors.add(colorPallete.get(i));
        }
    }
    
    public static void createCard(JButton button)
    {
        cards.add(new Card(button, colors.get(0)));
        colors.remove(0);
        Collections.shuffle(colors);
    }
    
    public static void turnCard(JButton button){
        
        Card aux = null;
        
        for (int i = 0; i < cards.size(); i++) {
            if(cards.get(i).button==button){
                aux = cards.get(i);
            }
        }
        
        checkTurnedCards(aux);
    }
    
    public static void checkTurnedCards(Card aux)
    {
        if(cardsTurned==0){
            aux_Card = aux;
            cardsTurned++;
            aux.button.setBackground(aux.color);
        }else
        {
            if(aux != aux_Card)
            {
                if(aux.color== aux_Card.color)
                {
                    aux.button.setBackground(aux.color);
                    cardsTurned=0;
                    score++;
                    ActionListener taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        //...Perform a task...
                            aux.button.setBackground(Color.white);
                            aux_Card.button.setBackground(Color.white);
                            aux.button.setEnabled(false);
                            aux_Card.button.setEnabled(false);
                            aux_Card=null;
                            cardsTurned=0;
                        }
                    };

                    Timer timer = new Timer(300 ,taskPerformer);
                    timer.setRepeats(false);
                    timer.start();
                    if(score==4)
                    {
                        Tablero.winGame();
                    }

                }else
                {
                    lifes--;
                    labelLife.setText(""+lifes);
                    aux.button.setBackground(aux.color);
                    if(lifes<=0)
                    {
                        for (int i = 0; i < cards.size(); i++) 
                        {
                            cards.get(i).button.setEnabled(false);
                        }
                        
                        Tablero.gameOver();
                    }

                    ActionListener taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        //...Perform a task...
                            aux.button.setBackground(Color.white);
                            aux_Card.button.setBackground(Color.white);
                            aux_Card=null;
                            cardsTurned=0;
                        }
                    };

                    Timer timer = new Timer(300 ,taskPerformer);
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            
        }
    }
}

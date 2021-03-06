package teste;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import getfour.AIException;
import getfour.GetFour;
import getfour.GetFourTree;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GoingS
 */
public class Teste {
    private GetFour getFourTest = new GetFour();
    
    public Teste() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    //@Test
    public void testGetFreeRow(){
        testGetFreeRow3();
        testGetFreeRow1();
        testGetFreeRow2();
    }
    
    //@Test
    public void testDiscsInARow(){
        testDiscsInARow1();
    }
    
    //@Test
    public void testGetFreeRandomColumn(){
        testGetFreeRandomColumn1();
        testGetFreeRandomColumn2(); 
    }
    
    @Test
    public void testAi(){
        testAi1();
        testAi2();
    }
    
    
    public void testGetFreeRow1(){
        int freeRow;
        
        // Esvaziamos a grid e esperamos que retorne 6
        // (ultima linha de cima para baixo) para todas as colunas
        for(int i=0; i<42; i++)
            GetFour.gridArray[i] = 0;
        
        // Testamos com todas as linhas
        for(int i=1; i<=7; i++){
            freeRow = GetFour.getFreeRow(GetFour.gridArray, i);
            assertEquals(freeRow, 6); 
        }
    }
    
    public void testGetFreeRow2(){
        int freeRow;
        
        // Preenchemos toda a grid e testamos todas as linhas,
        // Esperamos um valor 0, como descrito na função,
        // pois todas estão ocupadas.
        for(int i=0; i<42; i++)
            GetFour.gridArray[i] = 1;
        
        for(int i=1; i<=7; i++){
            freeRow = GetFour.getFreeRow(GetFour.gridArray, i);
            assertEquals(freeRow, 0);
        }
    }
    
    public void testGetFreeRow3(){
        int freeRow;
        
        // Preenchemos toda a primeira coluna, exceto a na primeira linha
        // Esperamos que retorne a primeira linha
        for(int i=0; i<42; i++)
            GetFour.gridArray[i] = 0;
        
        for(int i=1; i<=5; i++)
            GetFour.gridArray[i*7] = 1;
        
        freeRow = GetFour.getFreeRow(GetFour.gridArray, 1);
        assertEquals(freeRow, 1);
    }
    
    public void testDiscsInARow1(){
        int[] situation = new int[42];
        int redPlayer = 1;
        int yellowPlayer = -1;
        
        for(int i=0; i<42; i++)
            situation[i] = 0;
        
        // Linha de 2 vermelha horizontal
        for(int i=40; i<42; i++){
            situation[i] = redPlayer;
        }
        
        // Linha de 3 amarela vertical
        for(int i=0; i<3; i++)
            situation[i*7 + 4] = yellowPlayer;
        
        // Linha de 4 na diagonal
        for(int i=0; i<4; i++)
            situation[i*7+i] = redPlayer;
        
        // Verificamos se as linhas que acabamos de criar são detecatadas
        assertTrue(GetFour.discsInARow(situation, redPlayer, 2, false));
        assertTrue(GetFour.discsInARow(situation, redPlayer, 4, false));
        assertTrue(GetFour.discsInARow(situation, yellowPlayer, 3, false));
    }
    
    public void testGetFreeRandomColumn1(){
        int freeRandomColumn;
        boolean betweenRange = true;
        
        // Testamos qual a coluna retornada caso a grid esteja totalmente cheia
        // e se o valor retornado é uma coluna válida (entre 1 a 7)
        for(int i=0; i<42; i++)
            GetFour.gridArray[i] = 1;
        freeRandomColumn = getFourTest.getFreeRandomColumn();
        
        if(freeRandomColumn > 7 || freeRandomColumn < 1)
            betweenRange = false;
        
        assertTrue(betweenRange);
    }
    
    public void testGetFreeRandomColumn2(){
        int rng;
        int freeRandomColumn;
        Random rand = new Random();
        
        // Colocamos um espaço em branco em algum lugar aleatório para
        // verificar se essa coluna é selecionada
        
        for(int vezes = 0; vezes < 100; vezes++){
            for(int i=0; i<42; i++)
                GetFour.gridArray[i] = 1;
            
            rng = rand.nextInt(7) + 1; // Número entre 1 a 7
            GetFour.gridArray[rng - 1] = 0;
            
            freeRandomColumn = getFourTest.getFreeRandomColumn();
            assertEquals(freeRandomColumn, rng);
        }
    }
    
    //@Test
    public void testAi1(){
        Random rand = new Random();
        int pos1, pos2;
        int aiPos;
        boolean triedToBlock;
        
        // Horizontal
        for(int vezes=0; vezes < 100; vezes++){
            triedToBlock = false;
            for(int i =0; i<42; i++)
                getFourTest.gridArray[i] = 0;
            
            pos1 = rand.nextInt(6) + 35; // Uma coluna aleatória na última linha
            pos2 = pos1 + 1;             // E outra na próxima posição
            GetFour.gridArray[pos1] = 1;
            GetFour.gridArray[pos2] = 1;
            
            aiPos = getFourTest.ai() + 35 - 1;
            
            if(aiPos == pos1 - 1 || aiPos == pos2 + 1)
                triedToBlock = true;
            
            assertEquals(triedToBlock, true);
        }
    }
    
    public void testAi2(){
        Random rand = new Random();
        int row;
        int pos1, pos2, pos3;
        int aiPos;
        
        for(int vezes=0; vezes < 100; vezes++){
                for(int i =0; i<42; i++)
                    getFourTest.gridArray[i] = 0;
                
                //row = rand.nextInt(7);
                row = rand.nextInt(7);
                pos1 = row + 35;
                pos2 = pos1 - 7;
                pos3 = pos2 - 7;
                
                GetFour.gridArray[pos1] = 1;
                GetFour.gridArray[pos2] = 1;
                GetFour.gridArray[pos3] = 1;
                
                aiPos = getFourTest.ai() - 1; // ai() retorna coluna de 1 a 7,
                                              // e queremos de 0 a 6;
                assertEquals(aiPos, row);
        }
    }
    //@Test
    // hasWon(int[] situation): Testar com nenhum vencedor ou vários vencedores
    public void testHasWon(){
        for(int i=0; i<42; i++)
            GetFour.gridArray[i] = -1;
        
        System.out.println(getFourTest.hasWon());
    }
    /*    
    
    */
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puAyudanteDeFirma;

import cl.sagalid.applet.AyudanteDeFirma;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Agustín
 */
public class java {
    
    public java() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void PruebaDeFirmaPDF() {
     
         AyudanteDeFirma ayudante = new AyudanteDeFirma();
         ayudante.pasoUno_cargaEtoken("Agustin2010");
         ayudante.pasoDos_obtieneRutasParaFirmar("C:\\Users\\Agustín\\Desktop\\test.pdf");
         ayudante.pasoTres_firmaPdf();
         //ayudante.pasoCuatro_firmaXml();
         //ayudante.pasoCinco_validaFirma();
     
     }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.sagalid.implementacionPDF;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.Certificate;

/**
 *
 * @author Agustín
 */
public class firmaDocumento {
    PdfReader reader;
    FileOutputStream os;
    PdfStamper stamper;
    PdfSignatureAppearance appearance;
    ExternalDigest digest;
    ExternalSignature signature;
            
    public boolean firmar(String rutaAlPdf, String rutaAlPdfFirmado, String razonFirma,String ubicacionFirma, PrivateKey key,Provider p,Certificate[] chain){
        boolean boolExitoEnLaFirma = false;
        try {
            
            reader = new PdfReader(rutaAlPdf);
            os = new FileOutputStream(rutaAlPdfFirmado);
            stamper = PdfStamper.createSignature(reader, os, '\0',null,true);
            
            
            appearance = stamper.getSignatureAppearance();
            //appearance.setReason(razonFirma);
            //appearance.setLocation(ubicacionFirma);
            //appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "Firma Electrónica Avanzada");

            
            digest = new BouncyCastleDigest();
            signature = new PrivateKeySignature(key,"SHA-1", p.getName());
            MakeSignature.signDetached(appearance, digest, signature, chain,  null, null, null, 0, MakeSignature.CryptoStandard.CADES);
            boolExitoEnLaFirma=true; 
            
            //catch (IOException | DocumentException | GeneralSecurityException ex) 
        } 
        catch (IOException ex) {
            System.err.println("Error de lectura o escritura "+ex.getMessage());
        }
        catch (DocumentException ex){
            System.err.println("Error en la lectura de documento a firmar "+ex.getMessage());
        }
        catch (GeneralSecurityException ex){
            System.err.println("Error general de seguridad "+ex.getMessage());
        }
        
        return boolExitoEnLaFirma;
    }
    
}

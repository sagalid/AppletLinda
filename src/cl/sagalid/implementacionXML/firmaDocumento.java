/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.sagalid.implementacionXML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Agustín
 */
public class firmaDocumento {

    public boolean firmar(String rutaAlXml, String rutaAlXmlFirmado, PrivateKey key, Provider p, Certificate[] chain, PublicKey llavePublica) {
        boolean resultado = false;
        try {

            File fXmlFile = new File(rutaAlXml);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            DOMSignContext dsc = new DOMSignContext(key, doc.getDocumentElement());
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            javax.xml.crypto.dsig.Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null), Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);
            SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            KeyValue kv = kif.newKeyValue(llavePublica);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
            XMLSignature signature = fac.newXMLSignature(si, ki);
            signature.sign(dsc);
            resultado = true;

            OutputStream os;
            os = new FileOutputStream(rutaAlXmlFirmado);
           
            TransformerFactory tf = TransformerFactory.newInstance();
            javax.xml.transform.Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(os));

        } catch (TransformerException ex) {
            System.err.println("No se logró trasnformar "+ex.getMessage());
        }  catch (MarshalException ex) {
            System.err.println("Error en marshal "+ex.getMessage());
        } catch (XMLSignatureException ex) {
            System.err.println("Error en la firma de XML "+ex.getMessage());
        } catch (KeyException ex) {
            System.err.println("Error en la llave "+ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error no existe este algoritmo "+ex.getMessage());
        } catch (InvalidAlgorithmParameterException ex) {
            System.err.println("Error parametro de algoritmo "+ex.getMessage());
        } catch (SAXException ex) {
            System.err.println("Error en SAX "+ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error en la lecto escritura de un archivo "+ex.getMessage());
        } catch (ParserConfigurationException ex) {
            System.err.println("Error en parser "+ex.getMessage());
        }
        return resultado;
    }
}

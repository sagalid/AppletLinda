/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.sagalid.linda.etoken;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;

import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import java.util.Enumeration;

/**
 *
 * @author Agustín
 */
public class AccesoAlDispositivo {

    public boolean boolResultadoOperacion;
    /**
     *Configuración del dispositivo de firma electrónica avanzada
     */
    public ETokenConfig etokenConf;
    public Provider proveedor;
    public KeyStore almacenDeCertificados;
    public Enumeration e;
    public PrivateKey llavePrivada;
    public PublicKey llavePublica;
    /**
     * Cadena de certificado
     */
    public Certificate[] chain;

    public AccesoAlDispositivo() {
        this.boolResultadoOperacion = false;
    }

    public boolean CargarDispositivo(String strClaveDispositivo) {
        try {
            setEtokenConf(new ETokenConfig());
            setProveedor(new sun.security.pkcs11.SunPKCS11(getEtokenConf().getETokenConfigStream()));
            Security.addProvider(getProveedor());
            setAlmacenDeCertificados(KeyStore.getInstance("PKCS11"));
            getAlmacenDeCertificados().load(getEtokenConf().getETokenConfigStream(), strClaveDispositivo.toCharArray());
            setE(getAlmacenDeCertificados().aliases());
            String alias = String.valueOf(getE().nextElement());
            setLlavePrivada((PrivateKey) getAlmacenDeCertificados().getKey(alias, strClaveDispositivo.toCharArray()));
            setChain(getAlmacenDeCertificados().getCertificateChain(alias));
            setLlavePublica(getAlmacenDeCertificados().getCertificate(alias).getPublicKey());

            setBoolResultadoOperacion(true);
        } catch (KeyStoreException ex) {
            System.err.println("Favor verifique que el EToken se encuentre conectado..." + ex.getMessage());
        } catch (IOException ex) {
        } catch (NoSuchAlgorithmException ex) {
        } catch (CertificateException ex) {
        } catch (UnrecoverableKeyException ex) {
        }
        return isBoolResultadoOperacion();
    }

    /**
     * @return the boolResultadoOperacion
     */
    public boolean isBoolResultadoOperacion() {
        return boolResultadoOperacion;
    }

    /**
     * @param boolResultadoOperacion the boolResultadoOperacion to set
     */
    public void setBoolResultadoOperacion(boolean boolResultadoOperacion) {
        this.boolResultadoOperacion = boolResultadoOperacion;
    }

    /**
     * @return the etokenConf
     */
    public ETokenConfig getEtokenConf() {
        return etokenConf;
    }

    /**
     * @param etokenConf the etokenConf to set
     */
    public void setEtokenConf(ETokenConfig etokenConf) {
        this.etokenConf = etokenConf;
    }

    /**
     * @return the proveedor
     */
    public Provider getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Provider proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the almacenDeCertificados
     */
    public KeyStore getAlmacenDeCertificados() {
        return almacenDeCertificados;
    }

    /**
     * @param almacenDeCertificados the almacenDeCertificados to set
     */
    public void setAlmacenDeCertificados(KeyStore almacenDeCertificados) {
        this.almacenDeCertificados = almacenDeCertificados;
    }

    /**
     * @return the e
     */
    public Enumeration getE() {
        return e;
    }

    /**
     * @param e the e to set
     */
    public void setE(Enumeration e) {
        this.e = e;
    }

    /**
     * @return the llavePrivada
     */
    public PrivateKey getLlavePrivada() {
        return llavePrivada;
    }

    /**
     * @param llavePrivada the llavePrivada to set
     */
    public void setLlavePrivada(PrivateKey llavePrivada) {
        this.llavePrivada = llavePrivada;
    }

    /**
     * @return the chain
     */
    public Certificate[] getChain() {
        return chain;
    }

    /**
     * @param chain the chain to set
     */
    public void setChain(Certificate[] chain) {
        this.chain = chain;
    }

    /**
     * @return the llavePublica
     */
    public PublicKey getLlavePublica() {
        return llavePublica;
    }

    /**
     * @param llavePublica the llavePublica to set
     */
    public void setLlavePublica(PublicKey llavePublica) {
        this.llavePublica = llavePublica;
    }
}

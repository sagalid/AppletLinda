/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.sagalid.linda.etoken;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Agustín
 */
public final class ETokenConfig {

    private final String EtokenName = "eToken";
    private  String EtokenLibrary = "Favor instale los drivers del EToken...";
    private String os;
    private String arch;
    private String slotString;
    private String pkcs11config;
    private byte[] pkcs11configBytes;
    
    public ByteArrayInputStream getETokenConfigStream() {

        os = System.getProperty("os.name").toLowerCase();
        arch = System.getProperty("os.arch").toLowerCase();
        slotString = "";

        if (getOs().contains("windows")) {
            if (getArch().contains("amd64")) {
                this.EtokenLibrary = "c:\\windows\\system32\\eTPKCS11.dll";
            } else {
                this.EtokenLibrary = "c:\\windows\\system32\\eTPKCS11.dll";
            }
        } else if (getOs().contains("linux") || getOs().contains("unix")) {
            if (getArch().contains("amd64") || getArch().contains("x64") || getArch().contains("x86_64")) {
                this.EtokenLibrary = "/usr/lib64/libeTPkcs11.so";
            } else {
                this.EtokenLibrary = "/usr/lib/libeTPkcs11.so";
            }
        }
        
        if(getOs().contains("vista") || getOs().contains("seven") || getOs().contains("7") || getOs().contains("8") || getOs().contains("server")) {
            slotString = "\nslot=2";
        }

        pkcs11config = "name=" + this.getEtokenName() + "\nlibrary=" + this.getEtokenLibrary() + getSlotString();
        pkcs11configBytes = getPkcs11config().getBytes();
        return new ByteArrayInputStream(getPkcs11configBytes());
    }

    public String getEtokenName() {
        return this.EtokenName;
    }

    /**
     * @return the EtokenLibrary
     */
    public String getEtokenLibrary() {
        return EtokenLibrary;
    }

    /**
     * @return the os
     */
    public String getOs() {
        return os;
    }

    /**
     * @return the arch
     */
    public String getArch() {
        return arch;
    }

    /**
     * @return the slotString
     */
    public String getSlotString() {
        return slotString;
    }

    /**
     * @return the pkcs11config
     */
    public String getPkcs11config() {
        return pkcs11config;
    }

    /**
     * @return the pkcs11configBytes
     */
    public byte[] getPkcs11configBytes() {
        return pkcs11configBytes;
    }
}

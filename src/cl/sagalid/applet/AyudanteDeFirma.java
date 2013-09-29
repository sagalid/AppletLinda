/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.sagalid.applet;

import cl.sagalid.linda.etoken.AccesoAlDispositivo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Agustín
 */
public class AyudanteDeFirma {

    public boolean accesoEtokenLogrado;
    AccesoAlDispositivo accesoAlToken;
    private String rutasParaFirmar;
    private String[] arregloRutasParaFirmar;
    private List<String> listaRutasParaFirmarPDF = new ArrayList<>();
    private List<String> listaRutasParaFirmarXML = new ArrayList<>();
    boolean resultadoFirmaPdf = false;
    boolean resultadoFirmaXml = false;
    public int cantidadPdfParaFirma;
    public int cantidadXmlParaFirma;
    
    /**
     * Constructor de la clase
     */
    public AyudanteDeFirma() {
    }
    
    /**
     * Carga el dispositivo EToken al pasarle la clave
     * @param claveEtoken 
     */
    public void pasoUno_cargaEtoken(String claveEtoken){
        accesoAlToken = new AccesoAlDispositivo();
        System.out.println("Obteniendo acceso al EToken...");
        setAccesoEtokenLogrado(accesoAlToken.CargarDispositivo(claveEtoken));
    }
    
    public void pasoDos_obtieneRutasParaFirmar(String rutasSeparadasPorComa){
            setRutasParaFirmar(rutasSeparadasPorComa);
            //setRutasParaFirmar("C:\\Users\\asalasf\\Desktop\\test.pdf,C:\\Users\\asalasf\\Desktop\\test2.pdf");
            setArregloRutasParaFirmar(rutasParaFirmar.split(";"));
            for (int i = 0; i < arregloRutasParaFirmar.length; i++) {
                if (arregloRutasParaFirmar[i].endsWith(".pdf")) {
                    listaRutasParaFirmarPDF.add(arregloRutasParaFirmar[i]);
                }
                if (arregloRutasParaFirmar[i].endsWith(".xml")) {
                    listaRutasParaFirmarXML.add(arregloRutasParaFirmar[i]);
                }
            }
            setCantidadPdfParaFirma(listaRutasParaFirmarPDF.size());
            setCantidadXmlParaFirma(listaRutasParaFirmarXML.size());
    }
    
    public boolean pasoTres_firmaPdf(){
        
            Iterator iteradorPdf = listaRutasParaFirmarPDF.iterator();
            String rutaTempPdf;
            cl.sagalid.implementacionPDF.firmaDocumento objetoFirmaPDF = new cl.sagalid.implementacionPDF.firmaDocumento();
            resultadoFirmaPdf = false;
            while (iteradorPdf.hasNext()) {
                rutaTempPdf = iteradorPdf.next().toString();
                System.out.println("Firmando el archivo: " + rutaTempPdf);
                resultadoFirmaPdf = objetoFirmaPDF.firmar(rutaTempPdf, rutaTempPdf + "_firmado.pdf", "Certificación", "Chile", accesoAlToken.getLlavePrivada(), accesoAlToken.getProveedor(), accesoAlToken.getChain());
            }
            return resultadoFirmaPdf;
    }
    
    public boolean pasoCuatro_firmaXml(){
            Iterator iteradorXml = listaRutasParaFirmarXML.iterator();
            String rutaTempXml;
            cl.sagalid.implementacionXML.firmaDocumento objetoFirmaXML = new cl.sagalid.implementacionXML.firmaDocumento();
            resultadoFirmaXml = false;
            while (iteradorXml.hasNext()) {
                rutaTempXml = iteradorXml.next().toString();
                System.out.println("Firmando el archivo: " + rutaTempXml);
                resultadoFirmaXml = objetoFirmaXML.firmar(rutaTempXml, rutaTempXml + "_firmado.xml", accesoAlToken.getLlavePrivada(), accesoAlToken.getProveedor(), accesoAlToken.getChain(), accesoAlToken.getLlavePublica());
            }
            return resultadoFirmaXml;
    }
    
    public void pasoCinco_validaFirma(){
        if (!listaRutasParaFirmarPDF.isEmpty()) {
                if (resultadoFirmaPdf) {
                    System.out.println("Se firmaron correctamente los PDFs");
                } else {
                    System.err.println("No se logró firmar correctamente los PDFs");
                }
            }
            if (!listaRutasParaFirmarXML.isEmpty()) {
                if (resultadoFirmaXml) {
                    System.out.println("Se firmaron correctamente los XMLs");
                } else {
                    System.err.println("No se logró firmar correctamente los XMLs");
                }
            }
    }

    /**
     * @return the accesoEtokenLogrado
     */
    public boolean isAccesoEtokenLogrado() {
        return accesoEtokenLogrado;
    }

    /**
     * @param accesoEtokenLogrado the accesoEtokenLogrado to set
     */
    public void setAccesoEtokenLogrado(boolean accesoEtokenLogrado) {
        this.accesoEtokenLogrado = accesoEtokenLogrado;
    }

    /**
     * @return the rutasParaFirmar
     */
    public String getRutasParaFirmar() {
        return rutasParaFirmar;
    }

    /**
     * @param rutasParaFirmar the rutasParaFirmar to set
     */
    public void setRutasParaFirmar(String rutasParaFirmar) {
        this.rutasParaFirmar = rutasParaFirmar;
    }

    /**
     * @return the arregloRutasParaFirmar
     */
    public String[] getArregloRutasParaFirmar() {
        return arregloRutasParaFirmar;
    }

    /**
     * @param arregloRutasParaFirmar the arregloRutasParaFirmar to set
     */
    public void setArregloRutasParaFirmar(String[] arregloRutasParaFirmar) {
        this.arregloRutasParaFirmar = arregloRutasParaFirmar;
    }

    /**
     * @return the listaRutasParaFirmarPDF
     */
    public List<String> getListaRutasParaFirmarPDF() {
        return listaRutasParaFirmarPDF;
    }

    /**
     * @param listaRutasParaFirmarPDF the listaRutasParaFirmarPDF to set
     */
    public void setListaRutasParaFirmarPDF(List<String> listaRutasParaFirmarPDF) {
        this.listaRutasParaFirmarPDF = listaRutasParaFirmarPDF;
    }

    /**
     * @return the listaRutasParaFirmarXML
     */
    public List<String> getListaRutasParaFirmarXML() {
        return listaRutasParaFirmarXML;
    }

    /**
     * @param listaRutasParaFirmarXML the listaRutasParaFirmarXML to set
     */
    public void setListaRutasParaFirmarXML(List<String> listaRutasParaFirmarXML) {
        this.listaRutasParaFirmarXML = listaRutasParaFirmarXML;
    }

    /**
     * @return the cantidadPdfParaFirma
     */
    public int getCantidadPdfParaFirma() {
        return cantidadPdfParaFirma;
    }

    /**
     * @param cantidadPdfParaFirma the cantidadPdfParaFirma to set
     */
    public void setCantidadPdfParaFirma(int cantidadPdfParaFirma) {
        this.cantidadPdfParaFirma = cantidadPdfParaFirma;
    }

    /**
     * @return the cantidadXmlParaFirma
     */
    public int getCantidadXmlParaFirma() {
        return cantidadXmlParaFirma;
    }

    /**
     * @param cantidadXmlParaFirma the cantidadXmlParaFirma to set
     */
    public void setCantidadXmlParaFirma(int cantidadXmlParaFirma) {
        this.cantidadXmlParaFirma = cantidadXmlParaFirma;
    }
    
    
}

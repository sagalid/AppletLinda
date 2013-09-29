/**
 * *****************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *****************************************************************************
 */
package dndapplet.applet;

import java.applet.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;
import java.util.zip.*;
import netscape.javascript.JSObject;

/**
 * This applet allows users to select files from their file system using drag
 * and drop and upload them to a remote server. All files will be zipped and
 * sent to the server as a single file to improve upload performance. This
 * applet will use the HTTP protocol to communicate with the server.
 *
 */
public class DNDApplet extends Applet implements DropTargetListener, ActionListener {

    /**
     * This label shows the user the files they have selected and their status.
     */
    private JLabel m_statusLabel;
    /**
     * This is the button which starts the upload process
     */
    private JButton m_updload;
    /**
     * This button is a link to the display images page
     */
    private JButton m_showImages;
    /**
     * This is the list of files which will be uploaded
     */
    private ArrayList m_fileList = new ArrayList();

    /**
     * The init method creates all of the UI controls and performs all of the
     * layout of the UI.
     */
    public void init() {
        setLayout(new BorderLayout());
        Container main = new Container();
        add(main, BorderLayout.CENTER);

        GridBagLayout gbl = new GridBagLayout();
        main.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.6;
        gbc.weightx = 0.00001;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(1, 0, 1, 1);

        /*
         * This label will also serve as our drop target so we want to
         * make sure it is big enough to be easy to drop files on to it.
         */
        JLabel title = new JLabel("Arrastre y suelte archivos acá!");
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        DropTarget dt2 = new DropTarget(title, this);
        title.setBorder(new LineBorder(Color.black));
        title.setOpaque(true);
        main.add(title);
        gbl.setConstraints(title, gbc);

    }

    /**
     * This method handles actually uploading the selected files to the server.
     */
    private void putData() {
        try {
            for (int i = 0; i < m_fileList.size(); i++) {
                File f = (File) m_fileList.get(i);
                ZipEntry entry = new ZipEntry(f.getName());
                InputStream in = new FileInputStream(f);
                int read;
                byte[] buf = new byte[1024];

                while ((read = in.read(buf)) > 0) {
//                    out.write(buf, 0, read);
                }
            }
        } catch (Exception e) {
            System.err.println("Error en la obtención de rutas " + e.getMessage());
        } finally {
        }

        /*
         * Now that the upload is complete we can let the user know that it was a success.
         */
        m_statusLabel.setText("Upload complete");
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        // System.out.println("dragenter(" + dtde + ")");
    }

    public void dragOver(DropTargetDragEvent dtde) {
        // System.out.println("dragOver(" + dtde + ")");
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
        // System.out.println("dropActionChanged(" + dtde + ")");
    }

    /**
     * This method will be called when the user drops a file on our target
     * label.
     *
     * @param dtde
     */
    public void drop(DropTargetDropEvent dtde) {
        int action = dtde.getDropAction();

        /*
         * We have to tell Java that we are going to accept this drop before
         * we try to access any of the data.
         */
        dtde.acceptDrop(action);

        fromTransferable(dtde.getTransferable());

        /*
         * Once the drop event is complete we need to notify Java again so it
         * can reset the cursor and finish showing the drop behavior.
         */
        dtde.dropComplete(true);
    }

    /**
     * This is a helper method to get the data from the drop event.
     *
     * @param t
     */
    private void fromTransferable(Transferable t) {
        if (t == null) {
            return;
        }

        /*
         * The user may have dropped a file or anything else from any application
         * running on their computer.  This interaction is handled with data flavors.
         * For example, text copied from OpenOffice might have one flavor which 
         * contains the text with formatting information and another flavor which
         * contains the text without any of this information.  We need to look for
         * the data flavor we know how to support and read the list of files from it.
         */
        try {
            DataFlavor flavors[] = t.getTransferDataFlavors();
            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                /*
                 * We are looking for the list of files data flavor.  This will be a
                 * list of the paths to the files the user dragged and dropped on to
                 * our application.
                 */
                List list = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                m_fileList.addAll(list);
                Iterator iteradorRutas = m_fileList.iterator();
                String strRutaArchivosParaFirma = null;
                String strRutaTmp = null;
                while (iteradorRutas.hasNext()) {

                    if (strRutaArchivosParaFirma == null) {
                        strRutaArchivosParaFirma = iteradorRutas.next().toString();
                    } else {
                        strRutaTmp = iteradorRutas.next().toString();
                        strRutaArchivosParaFirma = strRutaArchivosParaFirma + ";" + strRutaTmp;
                    }
                }

                /*Proceso de obtención de rutas*/
                if (m_fileList.size() > 0) {
                    JSObject window = JSObject.getWindow(this);
                    String rutasParaFirma = "\"" + strRutaArchivosParaFirma + "\"";
                    rutasParaFirma = rutasParaFirma.replaceAll("\\\\", "\\\\\\\\");
                    System.out.println("Rutas para firmar: " + rutasParaFirma);
                    Object eval = window.eval("cargaRutas(" + rutasParaFirma + ")");
                    eval = window.eval("ejecutaAppletLinda();");
                    
                    
                }
            } else {
                /*
                 * There is a very large number of other data flavors the user can 
                 * drop onto our applet.  we will just show the information from 
                 * those types, but we can't get a list of files to upload from 
                 * them.
                 */
                DataFlavor df = DataFlavor.selectBestTextFlavor(flavors);

                JOptionPane.showMessageDialog(this, "df: " + df);

                JOptionPane.showMessageDialog(this, "t.getTransferData(df): " + t.getTransferData(df));
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

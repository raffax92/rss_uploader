/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myzoo.rssuploader;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import static com.myzoo.rssuploader.Main.apiKey;
import static com.myzoo.rssuploader.Main.xmlPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author fraffaini
 */
public class JFrameMain extends javax.swing.JFrame {
    
    String fileName;
    DbxClientV2 client;
    
    Vector<HashMap<String,Object>> itemTypes;
    Vector<HashMap<String,Object>> mimeTypes;
    
    public static final Logger LOGGER = LogManager.getLogger("Main");

    /**
     * Creates new form JFrameMain
     */
    public JFrameMain() throws ConfigurationException, FileNotFoundException, DbxException, IOException {
        initComponents();
        
        XMLConfiguration xmlConfig = new Configurations().xml("conf/properties.xml");
        
        apiKey = xmlConfig.configurationsAt("properties").get(0).getString("apiKey");
        xmlPath = xmlConfig.configurationsAt("properties").get(0).getString("xmlPath");
        
        //Apertura account Dropbox
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, apiKey);
        
        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathDisplay());
            }
            if (!result.getHasMore()) {
                break;
            }
            result = client.files().listFolderContinue(result.getCursor());
        }
        
        Date date = Calendar.getInstance().getTime();
        fileName = "xml/XML_" + (new SimpleDateFormat("yyyy_MM_dd")).format(date) + ".xml"; 
        
        //Caricamento combobox preimpostati
        itemTypes = new Vector<>();   
        HashMap<String,Object> type = new HashMap<>();
        type.put("NOME",             "");
        type.put("MASK_TITLE",       "");
        type.put("MASK_DESCRIPTION", "");
        itemTypes.add(type);
        jComboBoxType.addItem("");
        
        List<HierarchicalConfiguration<ImmutableNode>> fields = xmlConfig.configurationsAt("types.type");
        for(HierarchicalConfiguration sub : fields) {
            type = new HashMap<>();
            type.put("NOME",             sub.getString("nome"));
            type.put("MASK_TITLE",       sub.getString("maskTitle"));
            type.put("MASK_DESCRIPTION", sub.getString("maskDescription"));
            
            itemTypes.add(type);
            jComboBoxType.addItem(sub.getString("nome"));
        }
        
        //Caricamento combobox preimpostati
        mimeTypes = new Vector<>();   
        HashMap<String,Object> mime = new HashMap<>();
        mime.put("VALUE",       "");
        mime.put("DESCRIPTION", "");
        mime.put("DEF_LENGTH",  "");
        mimeTypes.add(mime);
        jComboBoxMimeType.addItem("");
        
        fields = xmlConfig.configurationsAt("mime.file_type");
        for(HierarchicalConfiguration sub : fields) {
            mime = new HashMap<>();
            mime.put("VALUE",       sub.getString("value"));
            mime.put("DESCRIPTION", sub.getString("description"));
            mime.put("DEF_LENGTH",  sub.getString("def_length"));
            
            mimeTypes.add(mime);
            jComboBoxMimeType.addItem(sub.getString("description"));
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonConferma = new javax.swing.JButton();
        jComboBoxType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldTitolo = new javax.swing.JTextField();
        jCheckBoxPermaLink = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescrizione = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLink = new javax.swing.JTextArea();
        jTextFieldGUID = new javax.swing.JTextField();
        jComboBoxMimeType = new javax.swing.JComboBox<>();
        jTextFieldLength = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonConferma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConferma.setText("Conferma");
        jButtonConferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfermaActionPerformed(evt);
            }
        });

        jComboBoxType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Tipo Item");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Titolo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Link");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Descrizione");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("GUID");

        jTextFieldTitolo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jCheckBoxPermaLink.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBoxPermaLink.setText("isPermaLink");
        jCheckBoxPermaLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPermaLinkActionPerformed(evt);
            }
        });

        jTextAreaDescrizione.setColumns(20);
        jTextAreaDescrizione.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextAreaDescrizione.setRows(2);
        jTextAreaDescrizione.setToolTipText("");
        jScrollPane1.setViewportView(jTextAreaDescrizione);

        jTextAreaLink.setColumns(20);
        jTextAreaLink.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextAreaLink.setRows(2);
        jScrollPane2.setViewportView(jTextAreaLink);

        jTextFieldGUID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jComboBoxMimeType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxMimeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMimeTypeActionPerformed(evt);
            }
        });

        jTextFieldLength.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Tipo File");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Lungh.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldTitolo, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonConferma, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldLength, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxMimeType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldGUID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBoxPermaLink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTitolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxPermaLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldGUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMimeType)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jButtonConferma, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfermaActionPerformed
        try{            
            Date date = Calendar.getInstance().getTime();
            String strDate = (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss XX", new Locale("en", "EN"))).format(date);
            
            if (mimeTypes.get(jComboBoxMimeType.getSelectedIndex()).get("VALUE") == null){
                JOptionPane.showMessageDialog(null, "Impostare il tipo di file");
                return;
            }
            
            //Download del file da Dropbox
            OutputStream outputStream = new FileOutputStream(fileName);
            FileMetadata metadata = client.files()
               .downloadBuilder(xmlPath)        
               .download(outputStream);
            outputStream.close();
            
            //Apertura file xml test
            File xmlFile = new File(fileName);
            //Create the documentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //Create the documentBuilder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //Create the Document  by parsing the file
            Document document = documentBuilder.parse(xmlFile);
            //Get the root element of the xml Document;
            Element documentElement = document.getDocumentElement();
            
            //Aggiornamento data radice
            NodeList nodeChild =  documentElement.getElementsByTagName("channel").item(0).getChildNodes();
            for(int i = 0; i < nodeChild.getLength(); i++){
                Node type = nodeChild.item(i);
                if (nodeChild.item(i).getNodeName().compareTo("lastBuildDate") == 0){
                    nodeChild.item(i).setTextContent(strDate);
                    break;
                }
            }
            
            NodeList nodeItems = documentElement.getElementsByTagName("item");
            NodeList nodeRadio;
            
            //Aggiornamento data ZooTV
            nodeRadio =  nodeItems.item(0).getChildNodes();
            for(int i = 0; i < nodeRadio.getLength(); i++){
                if (nodeRadio.item(i).getNodeName().compareTo("pubDate") == 0){
                    nodeRadio.item(i).setTextContent(strDate);
                    break;
                }
            }

            //Aggiornamento data ZooRadio
            nodeRadio =  nodeItems.item(1).getChildNodes();
            for(int i = 0; i < nodeRadio.getLength(); i++){
                if (nodeRadio.item(i).getNodeName().compareTo("pubDate") == 0){
                    nodeRadio.item(i).setTextContent(strDate);
                    break;
                }
            }

            //Aggiornamento data Radio105
            nodeRadio =  nodeItems.item(2).getChildNodes();
            for(int i = 0; i < nodeRadio.getLength(); i++){
                if (nodeRadio.item(i).getNodeName().compareTo("pubDate") == 0){
                    nodeRadio.item(i).setTextContent(strDate);
                    break;
                }
            }

            //Aggiornamento data DetoxRadio
            nodeRadio =  nodeItems.item(3).getChildNodes();
            for(int i = 0; i < nodeRadio.getLength(); i++){
                if (nodeRadio.item(i).getNodeName().compareTo("pubDate") == 0){
                    nodeRadio.item(i).setTextContent(strDate);
                    break;
                }
            }
            
            //Creazione nuovo nodo
            /*<item>
                <title>Zoo-14-04-2020-S@m</title>
                <link>https://d3ctxlq1ktw2nl.cloudfront.net/production/2020-3-14/64454293-44100-2-cb2cd411cdd1f.mp3?=14042020</link>
                <description>Zoo-Mar,14-04-2020-S@m.mp3</description>
                <pubDate>Tue, 14 Apr 2020 16:12:00 +0200</pubDate>
                <guid isPermaLink="false">64454293-44100-2-cb2cd411cdd1f</guid>
                <enclosure length="98123123" type="audio/mpeg" url="https://d3ctxlq1ktw2nl.cloudfront.net/production/2020-3-14/64454293-44100-2-cb2cd411cdd1f.mp3?=14042020"/>
            </item>*/
            
            Element titleElement = document.createElement("title");
            Element linkElement = document.createElement("link");
            Element descriptionElement = document.createElement("description");
            Element pubDateElement = document.createElement("pubDate");
            Element guidElement = document.createElement("guid");
            Element enclosureElement = document.createElement("enclosure");
            
            titleElement.setTextContent(jTextFieldTitolo.getText());
            linkElement.setTextContent(jTextAreaLink.getText());
            descriptionElement.setTextContent(jTextAreaDescrizione.getText());
            pubDateElement.setTextContent(strDate);

            if (jCheckBoxPermaLink.isSelected()){
                guidElement.setAttribute("isPermaLink", "true");
            } else {
                guidElement.setAttribute("isPermaLink", "false");
            }
            guidElement.setTextContent(jTextFieldGUID.getText());

            enclosureElement.setAttribute("length", jTextFieldLength.getText());
            enclosureElement.setAttribute("type", mimeTypes.get(jComboBoxMimeType.getSelectedIndex()).get("VALUE").toString());
            enclosureElement.setAttribute("url", jTextAreaLink.getText());

            Element itemNode = document.createElement("item");

            itemNode.appendChild(titleElement);
            itemNode.appendChild(linkElement);
            itemNode.appendChild(descriptionElement);
            itemNode.appendChild(pubDateElement);
            itemNode.appendChild(guidElement);
            itemNode.appendChild(enclosureElement);            
            
            //Posizionamento del nodo tra il 2o e il 3o ITEM
            documentElement.getElementsByTagName("channel").item(0).insertBefore(itemNode, nodeItems.item(4));

            //Creazione del file XML
            Transformer tFormer = TransformerFactory.newInstance().newTransformer();
            //  Set output file to xml
            tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            tFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
            //  Write the document back to the file
            Source source = new DOMSource(document);
            Result result = new StreamResult(new File("xml/xml_rss.xml"));
            tFormer.transform(source, result);
            
            //Cancello il file su dropbox
            //DeleteResult metadataDelete = client.files().deleteV2("/" + xmlPath);
            
            // Upload "xml_rss.xml" to Dropbox
            InputStream in = new FileInputStream("xml/xml_rss.xml");
            FileMetadata metadataFile = client.files().uploadBuilder(xmlPath)
                .withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(in);

        } catch (TransformerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (SAXException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (ParserConfigurationException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (DbxException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }//GEN-LAST:event_jButtonConfermaActionPerformed

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeActionPerformed
        
        HashMap<String,Object> value = itemTypes.get(jComboBoxType.getSelectedIndex());
        
        Date date = Calendar.getInstance().getTime();
        String dd   = (new SimpleDateFormat("dd")).format(date); 
        String MM   = (new SimpleDateFormat("MM")).format(date);
        String yyyy = (new SimpleDateFormat("yyyy")).format(date);
        String EEE  = (new SimpleDateFormat("EEE")).format(date);
        EEE = EEE.substring(0, 1).toUpperCase() + EEE.substring(1);
        
        if ( !value.get("MASK_TITLE").equals("") ){
            String maskTitle = value.get("MASK_TITLE").toString();
            maskTitle = maskTitle.replace("#DAY#", EEE).replace("#DD#", dd).replace("#MM#", MM).replace("#YYYY#", yyyy);
            jTextFieldTitolo.setText(maskTitle);
            
            String maskDescription = value.get("MASK_DESCRIPTION").toString();
            maskDescription = maskDescription.replace("#DAY#", EEE).replace("#DD#", dd).replace("#MM#", MM).replace("#YYYY#", yyyy);
            jTextAreaDescrizione.setText(maskDescription);
        } else {
            jTextFieldTitolo.setText("");
            jTextAreaDescrizione.setText("");
        }
        
    }//GEN-LAST:event_jComboBoxTypeActionPerformed

    private void jCheckBoxPermaLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPermaLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxPermaLinkActionPerformed

    private void jComboBoxMimeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMimeTypeActionPerformed
        HashMap<String,Object> value = mimeTypes.get(jComboBoxMimeType.getSelectedIndex());
        
        if ( !value.get("VALUE").equals("") ){
            jTextFieldLength.setText(value.get("DEF_LENGTH").toString());
        } else {
            jTextFieldLength.setText("");
        }
    }//GEN-LAST:event_jComboBoxMimeTypeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConferma;
    private javax.swing.JCheckBox jCheckBoxPermaLink;
    private javax.swing.JComboBox<String> jComboBoxMimeType;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaDescrizione;
    private javax.swing.JTextArea jTextAreaLink;
    private javax.swing.JTextField jTextFieldGUID;
    private javax.swing.JTextField jTextFieldLength;
    private javax.swing.JTextField jTextFieldTitolo;
    // End of variables declaration//GEN-END:variables
}

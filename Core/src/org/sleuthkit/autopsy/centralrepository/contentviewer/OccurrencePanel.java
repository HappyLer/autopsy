/*
 * Central Repository
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.centralrepository.contentviewer;

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.datamodel.TskData;

final class OccurrencePanel extends javax.swing.JPanel {

    private static final int LEFT_INSET = 10;
    private static final int RIGHT_INSET = 10;
    private static final int TOP_INSET = 10;
    private static final int BOTTOM_INSET = 10;
    private static final int VERTICAL_GAP = 6;
    private static final int HORIZONTAL_GAP = 4;
    private static final long serialVersionUID = 1L;

    private int gridY = 0;
    private final List<OtherOccurrenceNodeData> nodeData;
    private final Set<String> caseNaWmes = new HashSet<>();
    private final Set<String> dataSourceNames = new HashSet<>();
    private final Set<String> filePaths = new HashSet<>();

    /**
     * Creates new form OccurrencePanel2
     */
    OccurrencePanel(List<OtherOccurrenceNodeData> nodeDataList) {
        nodeData = nodeDataList;
        initComponents();
        if (!nodeData.isEmpty()) {
            addInstanceDetails();
            if (!filePaths.isEmpty()) {
                addFileDetails();
            }
            if (!dataSourceNames.isEmpty()) {
                addDataSourceDetails();
            }
            if (!caseNames.isEmpty()) {
                addCaseDetails();
            }
        }
        //add filler to keep everything else at the top
        addItemToBag(gridY, 0, 0, 0, new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767)));
    }

    @Messages({
        "OccurrencePanel.commonProperties.text=Common Properties",
        "OccurrencePanel.commonPropertyTypeLabel.text=Type:",
        "OccurrencePanel.commonPropertyValueLabel.text=Value:",
        "OccurrencePanel.commonPropertyKnownStatusLabel.text=Known Status:",
        "OccurrencePanel.commonPropertyCommentLabel.text=Comment:"
    })
    private void addInstanceDetails() {
        javax.swing.JLabel commonPropertiesLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(commonPropertiesLabel, Bundle.OccurrencePanel_commonProperties_text());
        commonPropertiesLabel.setFont(commonPropertiesLabel.getFont().deriveFont(Font.BOLD, commonPropertiesLabel.getFont().getSize()));
        addItemToBag(gridY, 0, TOP_INSET, 0, commonPropertiesLabel);
        gridY++;
        //for each other occurrence
        for (OtherOccurrenceNodeData occurrence : nodeData) {
            if (occurrence instanceof OtherOccurrenceNodeInstanceData) {
                String type = ((OtherOccurrenceNodeInstanceData) occurrence).getType();
                if (!type.isEmpty()) {
                    javax.swing.JLabel typeLabel = new javax.swing.JLabel();
                    org.openide.awt.Mnemonics.setLocalizedText(typeLabel, Bundle.OccurrencePanel_commonPropertyTypeLabel_text());
                    addItemToBag(gridY, 0, VERTICAL_GAP, 0, typeLabel);
                    javax.swing.JLabel typeFieldValue = new javax.swing.JLabel();
                    typeFieldValue.setText(type);
                    addItemToBag(gridY, 1, VERTICAL_GAP, 0, typeFieldValue);
                    gridY++;
                }
                String value = ((OtherOccurrenceNodeInstanceData) occurrence).getValue();
                if (!value.isEmpty()) {
                    javax.swing.JLabel valueLabel = new javax.swing.JLabel();
                    org.openide.awt.Mnemonics.setLocalizedText(valueLabel, Bundle.OccurrencePanel_commonPropertyValueLabel_text());
                    addItemToBag(gridY, 0, 0, 0, valueLabel);
                    javax.swing.JLabel valueFieldValue = new javax.swing.JLabel();
                    valueFieldValue.setText(value);
                    addItemToBag(gridY, 1, 0, 0, valueFieldValue);
                    gridY++;
                }
                TskData.FileKnown knownStatus = ((OtherOccurrenceNodeInstanceData) occurrence).getKnown();
                javax.swing.JLabel knownStatusLabel = new javax.swing.JLabel();
                org.openide.awt.Mnemonics.setLocalizedText(knownStatusLabel, Bundle.OccurrencePanel_commonPropertyKnownStatusLabel_text());
                addItemToBag(gridY, 0, 0, 0, knownStatusLabel);
                javax.swing.JLabel knownStatusValue = new javax.swing.JLabel();
                knownStatusValue.setText(knownStatus.toString());
                if (knownStatus == TskData.FileKnown.BAD) {
                    knownStatusValue.setForeground(Color.RED);
                }
                addItemToBag(gridY, 1, 0, 0, knownStatusValue);
                gridY++;

                String comment = ((OtherOccurrenceNodeInstanceData) occurrence).getComment();
                if (!comment.isEmpty()) {
                    javax.swing.JLabel commentLabel = new javax.swing.JLabel();
                    org.openide.awt.Mnemonics.setLocalizedText(commentLabel, Bundle.OccurrencePanel_commonPropertyCommentLabel_text());
                    addItemToBag(gridY, 0, 0, VERTICAL_GAP, commentLabel);
                    javax.swing.JTextArea commentValue = new javax.swing.JTextArea();
                    commentValue.setText(comment);
                    commentValue.setEditable(false);
                    commentValue.setColumns(20);
                    commentValue.setLineWrap(true);
                    commentValue.setRows(3);
                    commentValue.setTabSize(4);
                    commentValue.setWrapStyleWord(true);
                    commentValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    commentValue.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.disabledBackground"));
                    addItemToBag(gridY, 1, 0, VERTICAL_GAP, commentValue);
                    gridY++;
                }
                caseNames.add(((OtherOccurrenceNodeInstanceData) occurrence).getCaseName());
                dataSourceNames.add(((OtherOccurrenceNodeInstanceData) occurrence).getDataSourceName());
                filePaths.add(((OtherOccurrenceNodeInstanceData) occurrence).getFilePath());
            }
        }
        //end for each
    }

    @Messages({
        "OccurrencePanel.fileDetails.text=File Details",
        "OccurrencePanel.filePathLabel.text=File Path:"
    })
    private void addFileDetails() {

        javax.swing.JLabel fileDetailsLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(fileDetailsLabel, Bundle.OccurrencePanel_fileDetails_text());
        fileDetailsLabel.setFont(fileDetailsLabel.getFont().deriveFont(Font.BOLD, fileDetailsLabel.getFont().getSize()));
        addItemToBag(gridY, 0, TOP_INSET, 0, fileDetailsLabel);
        gridY++;
        String filePath = filePaths.size() > 1 ? "too many files" : filePaths.iterator().next();
        javax.swing.JLabel filePathLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(filePathLabel, Bundle.OccurrencePanel_filePathLabel_text());
        addItemToBag(gridY, 0, VERTICAL_GAP, VERTICAL_GAP, filePathLabel);
        javax.swing.JTextArea filePathValue = new javax.swing.JTextArea();
        filePathValue.setText(filePath);
        filePathValue.setEditable(false);
        filePathValue.setColumns(20);
        filePathValue.setLineWrap(true);
        filePathValue.setRows(3);
        filePathValue.setTabSize(4);
        filePathValue.setWrapStyleWord(true);
        filePathValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        filePathValue.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.disabledBackground"));
        addItemToBag(gridY, 1, VERTICAL_GAP, VERTICAL_GAP, filePathValue);
        gridY++;
    }

    @Messages({
        "OccurrencePanel.dataSourceDetails.text=Data Source Details",
        "OccurrencePanel.dataSourceNameLabel.text=Name:"
    })
    private void addDataSourceDetails() {
        javax.swing.JLabel dataSourceDetailsLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(dataSourceDetailsLabel, Bundle.OccurrencePanel_dataSourceDetails_text());
        dataSourceDetailsLabel.setFont(dataSourceDetailsLabel.getFont().deriveFont(Font.BOLD, dataSourceDetailsLabel.getFont().getSize()));
        addItemToBag(gridY, 0, TOP_INSET, 0, dataSourceDetailsLabel);
        gridY++;
        String dataSourceName = dataSourceNames.size() > 1 ? "too many data sources" : dataSourceNames.iterator().next();
        javax.swing.JLabel dataSourceNameLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(dataSourceNameLabel, Bundle.OccurrencePanel_dataSourceNameLabel_text());
        addItemToBag(gridY, 0, VERTICAL_GAP, VERTICAL_GAP, dataSourceNameLabel);
        javax.swing.JLabel dataSourceNameValue = new javax.swing.JLabel();
        dataSourceNameValue.setText(dataSourceName);
        addItemToBag(gridY, 1, VERTICAL_GAP, VERTICAL_GAP, dataSourceNameValue);
        gridY++;
    }

    @Messages({
        "OccurrencePanel.caseDetails.text=Case Details",
        "OccurrencePanel.caseNameLabel.text=Name:",
        "OccurrencePanel.caseCreatedDateLabel.text=Created Date:"
    })
    private void addCaseDetails() {
        javax.swing.JLabel caseDetailsLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(caseDetailsLabel, Bundle.OccurrencePanel_caseDetails_text());
        caseDetailsLabel.setFont(caseDetailsLabel.getFont().deriveFont(Font.BOLD, caseDetailsLabel.getFont().getSize()));
        addItemToBag(gridY, 0, TOP_INSET, 0, caseDetailsLabel);
        gridY++;
        String caseName = caseNames.size() > 1 ? "too many cases" : caseNames.iterator().next();
        javax.swing.JLabel caseNameLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(caseNameLabel, Bundle.OccurrencePanel_caseNameLabel_text());
        addItemToBag(gridY, 0, VERTICAL_GAP, 0, caseNameLabel);
        javax.swing.JLabel caseNameValue = new javax.swing.JLabel();
        caseNameValue.setText(caseName);
        addItemToBag(gridY, 1, VERTICAL_GAP, 0, caseNameValue);
        gridY++;
        String caseCreatedDate = caseNames.size() > 1 ? "too many cases" : caseNames.iterator().next();
        javax.swing.JLabel caseCreatedLabel = new javax.swing.JLabel();
        org.openide.awt.Mnemonics.setLocalizedText(caseCreatedLabel, Bundle.OccurrencePanel_caseCreatedDateLabel_text());
        addItemToBag(gridY, 0, 0, BOTTOM_INSET, caseCreatedLabel);
        javax.swing.JLabel caseCreatedValue = new javax.swing.JLabel();
        caseCreatedValue.setText("fakecreateddate");
        addItemToBag(gridY, 1, 0, BOTTOM_INSET, caseCreatedValue);
        gridY++;
    }

    private void addItemToBag(int gridYLocation, int gridXLocation, int topInset, int bottomInset, javax.swing.JComponent item) {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = gridXLocation;
        gridBagConstraints.gridy = gridYLocation;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        int leftInset = LEFT_INSET;
        int rightInset = HORIZONTAL_GAP;
        //change formating a bit if it is the value instead of the label
        if (gridXLocation == 1) {
            leftInset = 0;
            rightInset = RIGHT_INSET;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.gridwidth = 2;
        }
        gridBagConstraints.insets = new java.awt.Insets(topInset, leftInset, bottomInset, rightInset);
        //if the item is a filler item ensure it will resize vertically
        if (item instanceof javax.swing.Box.Filler) {
            gridBagConstraints.weighty = 0.1;
        }
        add(item, gridBagConstraints);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMinimumSize(new java.awt.Dimension(50, 30));
        setPreferredSize(null);
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

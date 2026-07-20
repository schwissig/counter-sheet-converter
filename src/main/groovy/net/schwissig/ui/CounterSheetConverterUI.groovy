package net.schwissig.ui

import net.schwissig.CounterSheetConverter

import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.filechooser.FileSystemView
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class CounterSheetConverterUI {

    CounterSheetConverterUI() {
        JFrame frame = new JFrame("Counter Sheet Converter")
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

        frame.addWindowListener(new WindowAdapter() {
            void windowClosing(WindowEvent windowEvent){
                System.exit(0)
            }
        })

        JLabel counterSheetInfo = new JLabel("Counter sheet file must reside in the same directory as the JSON data file.")

        JPanel infoPanel = new JPanel()
        infoPanel.add(counterSheetInfo)

        JPanel controlPanel = new JPanel()
        controlPanel.setLayout(new FlowLayout())

        JLabel dataFileLabel = new JLabel("JSON Data File: ", JLabel.RIGHT)
        JTextField dataFileField = new JTextField(40)
        JButton dataFileBtn = new JButton("Browse...")
        dataFileBtn.addActionListener {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory())

            int r = j.showDialog(frame, "Select")

            if (r == JFileChooser.APPROVE_OPTION) {
                dataFileField.setText(j.getSelectedFile().getAbsolutePath());
            }
        }

        JLabel countersOutputLabel = new JLabel("Counter Output Directory: ", JLabel.CENTER)
        JTextField counterOutputField = new JTextField(40)
        JButton outputDirBtn = new JButton("Browse...")
        outputDirBtn.addActionListener {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory())
            j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)

            int r = j.showDialog(frame, "Select")

            if (r == JFileChooser.APPROVE_OPTION) {
                counterOutputField.setText(j.getSelectedFile().getAbsolutePath());
            }
        }

        JButton button = new JButton("Convert")
        button.addActionListener {
            try {
                // TODO Run this in a thread and disable the button until the process is complete.
                CounterSheetConverter.convert(dataFileField.getText(), counterOutputField.getText())
            } catch (Exception exception) {
                println "Error: ${exception.getMessage()}"
            }
        }

        controlPanel.add(dataFileLabel)
        controlPanel.add(dataFileField)
        controlPanel.add(dataFileBtn)

        controlPanel.add(countersOutputLabel)
        controlPanel.add(counterOutputField)
        controlPanel.add(outputDirBtn)

        controlPanel.add(button)

        JPanel mainPanel = new JPanel()
        mainPanel.setLayout(new BorderLayout())

        mainPanel.add(infoPanel, BorderLayout.NORTH)
        mainPanel.add(controlPanel)

        frame.add(mainPanel)
        frame.setSize(750, 175)
        frame.setVisible(true)
    }
}

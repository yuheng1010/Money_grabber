import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
public class HPanel extends JPanel{
	private static final int FIELD_WIDTH=20;
	private JLabel iconLabel;
	private JPanel pricePanel;
	private JTextField priceField;
	private JLabel priceLabel;
	private JPanel preferPanel;
	private JCheckBox pcb1;
	private JCheckBox pcb2;
	private JCheckBox pcb3;
	private JCheckBox pcb4;
	private JLabel preferLabel;
	private JPanel timePanel;
	private JLabel timeLabel;
	private JComboBox<String>timeCombo;
	private JPanel OPanel;
	private JPanel addPanel;
	private ExecSQL e;
	private JButton nextB;
	private RManage manager;
	public HPanel(){
		createIcon();
		createPrice();
		createCheck();
		createTimeComp();
		createNextButton();
		createPanel();
	}
	public void createIcon() {
		ImageIcon icon=new ImageIcon("hp_logo.png");
		iconLabel=new JLabel(icon);
		iconLabel.setSize(400,200);
	}
	public void createPrice() {
		priceLabel=new JLabel("Price:");
		priceField=new JTextField(FIELD_WIDTH);
		priceField.setText("0");
	}
	public void createCheck() {
		preferLabel=new JLabel("Preference:");
		pcb1=new JCheckBox("Taiwanese");
		pcb2=new JCheckBox("Japanese");
		pcb3=new JCheckBox("Western");
		pcb4=new JCheckBox("Korean");
	}
	public void createTimeComp() {
		timeLabel=new JLabel("Distance:");
		timeCombo=new JComboBox<String>();
		timeCombo.addItem("10min");
		timeCombo.addItem("20min");
		timeCombo.addItem("30min");
	}
	public void createNextButton() {
		nextB=new JButton("Done!");
		nextB.setBackground(new Color(83,93,31));
		nextB.setForeground(Color.WHITE);
	}
	public void addButtonListener(JPanel panel1, RManage manager, JMenuBar mb) throws SQLException {
		this.manager=manager;
		CardLayout cardlayout=(CardLayout)panel1.getLayout();
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e1) {
				String typeChoice="";
				if(pcb1.isSelected()) {
					typeChoice+="T";
				}
				if(pcb2.isSelected()) {
					typeChoice+="J";
				}
				if(pcb3.isSelected()) {
					typeChoice+="W";
				}
				if(pcb4.isSelected()) {
					typeChoice+="K";
				}
				if(Integer.parseInt(priceField.getText())>0&&typeChoice!=null&&timeCombo.getSelectedItem()!=null) {
					manager.addDemand(Integer.parseInt(priceField.getText()), typeChoice, timeCombo.getSelectedItem().toString().substring(0,2));
					try {		
						e=new ExecSQL(manager);
						e.execute();
						//e.getAddress("樂山食堂");
					}catch(SQLException e2){
						e2.getMessage();
						System.out.print(e2.getMessage());
					}
					cardlayout.show(panel1,"mappage");
					mb.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null,"Please fill in all the options.","Oops!",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		ActionListener listener=new ClickListener();
		nextB.addActionListener(listener);
	}
	public void createPanel() {
		pricePanel=new JPanel();
		pricePanel.add(priceLabel);
		pricePanel.add(priceField);
		preferPanel=new JPanel();
		preferPanel.add(preferLabel);
		preferPanel.add(pcb1);
		preferPanel.add(pcb2);
		preferPanel.add(pcb3);
		preferPanel.add(pcb4);
		timePanel=new JPanel();
		timePanel.add(timeLabel);
		timePanel.add(timeCombo);
		BorderLayout borderlayout1=new BorderLayout();
		OPanel=new JPanel();
		OPanel.setLayout(borderlayout1);
		OPanel.add(pricePanel,BorderLayout.NORTH);
		OPanel.add(preferPanel,BorderLayout.CENTER);
		OPanel.add(timePanel,BorderLayout.SOUTH);
		BorderLayout borderlayout2=new BorderLayout();
		addPanel=new JPanel();
		addPanel.setLayout(borderlayout2);
		addPanel.add(iconLabel,BorderLayout.NORTH);
		addPanel.add(OPanel,BorderLayout.CENTER);
		addPanel.add(nextB,BorderLayout.SOUTH);
		add(addPanel);
	}
}

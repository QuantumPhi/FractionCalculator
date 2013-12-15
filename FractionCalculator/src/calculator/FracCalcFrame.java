package calculator;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FracCalcFrame extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6418140561389284182L;
	public FracCalcFrame() {
        initComponents();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	GroupLayout layout = new GroupLayout((JComponent)getContentPane());
    	getContentPane().setLayout(layout);
        jTextField1 = new javax.swing.JTextField();
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jScrollPane1 = new javax.swing.JScrollPane();
        {
        	jTextPane1 = new JTextPane();
        	jScrollPane1.setViewportView(jTextPane1);
        }
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 16));
        jButton1.setText("Calculate");
        layout.setVerticalGroup(layout.createSequentialGroup()
        	.addContainerGap()
        	.addComponent(getJSpinner1(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, Short.MAX_VALUE)
        	.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
        	.addContainerGap(28, 28));
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addGap(6)
        	.addGroup(layout.createParallelGroup()
        	    .addGroup(layout.createSequentialGroup()
        	        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
        	        .addGap(0, 0, Short.MAX_VALUE))
        	    .addGroup(layout.createSequentialGroup()
        	        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
        	        .addGap(0, 0, Short.MAX_VALUE))
        	    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
        	        .addGap(139)
        	        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	        .addGap(0, 16, Short.MAX_VALUE)
        	        .addComponent(getJSpinner1(), GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
        	        .addGap(0, 6, GroupLayout.PREFERRED_SIZE)))
        	.addGap(6));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        this.setTitle("Fraction Calculator");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    	this.jTextPane1.setText("");
    	String input = this.jTextField1.getText();
    	this.jTextField1.setText("");
    	if(!input.equals(""))
    		FractionCalculator.framework(input, this.jTextPane1, this.jTextField1);
    	else
    		this.jTextPane1.setText("Please enter an expression!");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FracCalcFrame().setVisible(true);
            }
        });
    }
    private JSpinner getJSpinner1() {
    	if (jSpinner1 == null) {
    		SpinnerListModel jSpinner1Model = 
    				new SpinnerListModel(
    						new String[] { "IntFraction", "BigFraction" });
    		jSpinner1 = new JSpinner();
    		jSpinner1.setModel(jSpinner1Model);
    		jSpinner1.addChangeListener(new ChangeListener() {
    			public void stateChanged(ChangeEvent evt) {
    				FractionCalculator.FRACTION_TYPE = jSpinner1.getValue().equals("IntFraction") ? true : false;
    			}
    		});
    	}
    	return jSpinner1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private JSpinner jSpinner1;
    private JTextPane jTextPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

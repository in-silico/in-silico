/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmBJ.java
 *
 * Created on 23/02/2011, 10:57:04 AM
 */

package blackjack;

import javax.swing.JOptionPane;

/**
 *
 * @author seb
 */
public class frmBJ extends javax.swing.JPanel {

    BlackJack bj;

    /** Creates new form frmBJ */
    public frmBJ() {
        initComponents();        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmdNewGame = new javax.swing.JButton();
        cmdOtra = new javax.swing.JButton();
        cmdStop = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPlayer1 = new javax.swing.JLabel();
        lblPlayer2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();

        cmdNewGame.setText("Nuevo Juego");
        cmdNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewGameActionPerformed(evt);
            }
        });
        jPanel1.add(cmdNewGame);

        cmdOtra.setText("Deme otra");
        cmdOtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOtraActionPerformed(evt);
            }
        });
        jPanel1.add(cmdOtra);

        cmdStop.setText("No mas cartas");
        cmdStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStopActionPerformed(evt);
            }
        });
        jPanel1.add(cmdStop);

        jLabel1.setText("Jugador 1:");

        lblPlayer1.setText("*");

        lblPlayer2.setText("*");

        jLabel4.setText("Jugador 2:");

        jLabel2.setText("Status:");

        lblStatus.setText("Haga click en nuevo juego");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPlayer2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblPlayer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblPlayer2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblStatus)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdOtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOtraActionPerformed
        // TODO add your handling code here:
        int status = bj.anotherCard();
        refrescar();
        hasSomeoneWon(status);
    }//GEN-LAST:event_cmdOtraActionPerformed

    private void cmdNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewGameActionPerformed
        // TODO add your handling code here:
        bj = new BlackJack(2);
        refrescar();
    }//GEN-LAST:event_cmdNewGameActionPerformed

    private void cmdStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStopActionPerformed
        // TODO add your handling code here:
        int status = bj.noMoreCards();
        refrescar();
        hasSomeoneWon(status);
    }//GEN-LAST:event_cmdStopActionPerformed

    void hasSomeoneWon(int status) {
        if (status != -1) {
            lblStatus.setText("Juego terminado, haga click en nuevo juego");
            if (status == -2)
                JOptionPane.showMessageDialog(this, "Empate!!!");
            else
                JOptionPane.showMessageDialog(this, "Gano jugador " +
                        (status+1) + "!!!");
            lblPlayer1.setText(bj.verCartas(0, false));
            lblPlayer2.setText(bj.verCartas(1, false));
        }
    }

    void refrescar() {
        int turn = bj.getTurn();
        lblPlayer1.setText(bj.verCartas(0, turn==1));
        lblPlayer2.setText(bj.verCartas(1, turn==0));
        lblStatus.setText("El turno es del jugador " + (turn+1));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdNewGame;
    private javax.swing.JButton cmdOtra;
    private javax.swing.JButton cmdStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblPlayer2;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables

}
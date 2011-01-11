package vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import analisis.Indicador;
import analisis.Rangos.Rango;

public class RangoGrafico extends JPanel {

	private static final long serialVersionUID = -7783062359910099807L;
	
	private Rango rango;
	private GraficaProgreso graficaProgreso;
    private JCheckBox invertido;
    private GraficaIndicador graficaIndicador;
    private Indicador indicador;
    private JSlider maximo;
    private JSlider minimo;
    private JLabel nombre;
    
    public RangoGrafico(Rango original, Rango r, GraficaProgreso g, GraficaIndicador gI, String nombreS, int espacio, Indicador i)
    {
    	rango = r;
    	graficaProgreso = g;
    	indicador = i;
    	graficaIndicador = gI;
        setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;
        nombre = new JLabel(nombreS);
        nombre.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
        invertido = new javax.swing.JCheckBox();
        invertido.setText("invertido");
        minimo = new JSlider((int) original.getMinimo(), (int) original.getMaximo()); 
        minimo.setValue((int) rango.getMinimo());
        minimo.setPreferredSize(new Dimension(600, 39));
        minimo.setMinorTickSpacing(Math.min(1, espacio));
        minimo.setMajorTickSpacing(espacio);
        minimo.setPaintTicks(true);
        minimo.setSnapToTicks(true);
        minimo.setPaintLabels(true);
        minimo.setLabelTable(minimo.createStandardLabels(Math.max(espacio, 4)));
        minimo.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				if(minimo.getValue() > rango.getMaximo())
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	minimo.setValue((int) rango.getMaximo());
            				rango.setMinimo(minimo.getValue());	
                        }
                    });
				}	
				else
					rango.setMinimo(minimo.getValue());
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
			}
		});
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        add(minimo, gridBagConstraints);
        add(nombre, new GridBagConstraints());
        maximo = new JSlider((int) original.getMinimo(), (int) original.getMaximo());
        maximo.setValue((int) rango.getMaximo());
        maximo.setPreferredSize(new Dimension(600, 39));
        maximo.setMinorTickSpacing(Math.min(1, espacio));
        maximo.setMajorTickSpacing(espacio);
        maximo.setPaintTicks(true);
        maximo.setSnapToTicks(true);
        maximo.setPaintLabels(true);
        maximo.setLabelTable(maximo.createStandardLabels(Math.max(espacio, 4)));
        maximo.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				if(maximo.getValue() < rango.getMinimo())
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	maximo.setValue((int) rango.getMinimo());
                        	rango.setMaximo(maximo.getValue());
                        }
                    });
				}
				else
					rango.setMaximo(maximo.getValue());
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
			}
		});
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(maximo, gridBagConstraints);
        invertido.addActionListener(new ActionListener() 
        {	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				rango.setInvertido(invertido.isSelected());
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
			}
		});
        invertido.setText("invertido");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(invertido, gridBagConstraints);
    }
}
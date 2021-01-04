/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.cel4j.
 * 
 * org.macroing.cel4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.cel4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.cel4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.cel4j.artifact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

final class GUI {
	private final JFrame jFrame;
	private final JMenu jMenu;
	private final JMenuBar jMenuBar;
	private final JMenuItem jMenuItem;
	private final JScrollPane jScrollPane1;
	private final JScrollPane jScrollPane2;
	private final JSplitPane jSplitPane;
	private final JTextArea jTextArea;
	private final JTextAreaOutputStreamDecorator jTextAreaOutputStreamDecoratorErr;
	private final JTextAreaOutputStreamDecorator jTextAreaOutputStreamDecoratorOut;
	private final JTextPane jTextPane;
	private final JToolBar jToolBar;
	private final String extension;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private GUI(final String extension) {
		this.extension = extension;
		this.jFrame = new JFrame();
		this.jMenu = new JMenu();
		this.jMenuBar = new JMenuBar();
		this.jMenuItem = new JMenuItem();
		this.jScrollPane1 = new JScrollPane();
		this.jScrollPane2 = new JScrollPane();
		this.jSplitPane = new JSplitPane();
		this.jTextArea = new JTextArea();
		this.jTextAreaOutputStreamDecoratorErr = new JTextAreaOutputStreamDecorator(this.jTextArea, System.err);
		this.jTextAreaOutputStreamDecoratorOut = new JTextAreaOutputStreamDecorator(this.jTextArea, System.out);
		this.jTextPane = new JJavaTextPane();
		this.jToolBar = new JToolBar();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void start() {
		doConfigureComponents();
		doConfigureSystemErr();
		doConfigureSystemOut();
		doConfigureThread();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void start(final String extension) {
		Objects.requireNonNull(extension, "extesion == null");
		
		SwingUtilities.invokeLater(() -> {
			final
			GUI gUI = new GUI(extension);
			gUI.start();
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doConfigureComponents() {
		doConfigureJFrame();
		doConfigureJMenu();
		doConfigureJMenuBar();
		doConfigureJMenuItem();
		doConfigureJScrollPane1();
		doConfigureJScrollPane2();
		doConfigureJSplitPane();
		doConfigureJTextArea();
		doConfigureJTextPane();
		doConfigureJToolBar();
	}
	
	private void doConfigureJFrame() {
		this.jFrame.getContentPane().setLayout(new BorderLayout());
		this.jFrame.getContentPane().add(this.jToolBar, BorderLayout.NORTH);
		this.jFrame.getContentPane().add(this.jSplitPane, BorderLayout.CENTER);
		this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jFrame.setJMenuBar(this.jMenuBar);
		this.jFrame.setSize(800, 600);
		this.jFrame.setTitle(String.format("Artifact - %s", doGetVersion()));
		this.jFrame.setLocationRelativeTo(null);
		this.jFrame.setVisible(true);
	}
	
	private void doConfigureJMenu() {
		this.jMenu.add(this.jMenuItem);
		this.jMenu.setText("File");
	}
	
	private void doConfigureJMenuBar() {
		this.jMenuBar.add(this.jMenu);
	}
	
	private void doConfigureJMenuItem() {
		this.jMenuItem.addActionListener(doCreateNewExitActionListener(this.jFrame));
		this.jMenuItem.setText("Exit");
	}
	
	private void doConfigureJScrollPane1() {
		this.jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.jScrollPane1.setViewportView(this.jTextPane);
	}
	
	private void doConfigureJScrollPane2() {
		this.jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.jScrollPane2.setViewportView(this.jTextArea);
	}
	
	private void doConfigureJSplitPane() {
		this.jSplitPane.setDividerLocation(300);
		this.jSplitPane.setLeftComponent(this.jScrollPane1);
		this.jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.jSplitPane.setRightComponent(this.jScrollPane2);
	}
	
	private void doConfigureJTextArea() {
		this.jTextArea.setBackground(Color.BLACK);
		this.jTextArea.setEditable(false);
		this.jTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		this.jTextArea.setForeground(Color.WHITE);
		this.jTextArea.setSelectedTextColor(Color.BLACK);
		this.jTextArea.setSelectionColor(Color.WHITE);
	}
	
	private void doConfigureJTextPane() {
		this.jTextPane.getActionMap().put("Evaluate", new EvaluatingAction(this.jTextPane, this.extension));
		this.jTextPane.getInputMap().put(KeyStroke.getKeyStroke("F5"), "Evaluate");
	}
	
	private void doConfigureJToolBar() {
		this.jToolBar.add(new EvaluatingAction(this.jTextPane, this.extension)).setIcon(doCreateIcon("Evaluate.png", "Evaluate"));
		this.jToolBar.setFloatable(false);
	}
	
	private void doConfigureSystemErr() {
		System.setErr(new PrintStream(this.jTextAreaOutputStreamDecoratorErr));
	}
	
	private void doConfigureSystemOut() {
		System.setOut(new PrintStream(this.jTextAreaOutputStreamDecoratorOut));
	}
	
	private void doConfigureThread() {
		Thread.setDefaultUncaughtExceptionHandler(doCreateNewUncaughtExceptionHandler(this.jTextArea));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static ActionListener doCreateNewExitActionListener(final JFrame jFrame) {
		return e -> {
			switch(JOptionPane.showConfirmDialog(jFrame, "Are you sure you want to quit?")) {
				case JOptionPane.OK_OPTION:
					System.exit(0);
					
					break;
				default:
					break;
			}
		};
	}
	
	private static Icon doCreateIcon(final String path, final String description) {
		final URL uRL = GUI.class.getResource(Objects.requireNonNull(path, "path == null"));
		
		if(uRL != null) {
			return new ImageIcon(uRL, Objects.requireNonNull(description, "description == null"));
		}
		
		return null;
	}
	
	private static String doGetVersion() {
		final Package package_ = GUI.class.getPackage();
		
		if(package_ != null) {
			final String implementationVersion = package_.getImplementationVersion();
			
			if(implementationVersion != null) {
				return String.format("v.%s", implementationVersion);
			}
		}
		
		return "v.0.0.0";
	}
	
	private static UncaughtExceptionHandler doCreateNewUncaughtExceptionHandler(final JTextArea jTextArea) {
		return (thread, throwable) -> jTextArea.append(Throwables.getStackTrace(throwable, thread) + "\n");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class EvaluatingAction extends AbstractAction {
		private static final ScriptEngineManager DEFAULT_SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();
		private static final long serialVersionUID = 1L;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private final JTextPane jTextPane;
		private final ScriptEngine scriptEngine;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public EvaluatingAction(final JTextPane jTextPane, final String extension) {
			this.jTextPane = Objects.requireNonNull(jTextPane, "jTextPane == null");
			this.scriptEngine = DEFAULT_SCRIPT_ENGINE_MANAGER.getEngineByExtension(extension);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public void actionPerformed(final ActionEvent actionEvent) {
			try {
				this.scriptEngine.eval(this.jTextPane.getDocument().getText(0, this.jTextPane.getDocument().getLength()));
			} catch(final BadLocationException | NullPointerException | ScriptException e) {
				Throwables.handleThrowable(e);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class JJavaTextPane extends JTextPane {
		private static final long serialVersionUID = 1L;
		private static final Pattern PATTERN_1 = Pattern.compile("\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|double|do|else|enum|extends|false|finally|final|float|for|if|goto|implements|import|instanceof|interface|int|long|new|null|package|private|protected|public|return|short|static|strictf|super|switch|synchronized|this|throws|throw|transient|true|try|void|volatile|while)\\b");
		private static final Pattern PATTERN_2 = Pattern.compile("\"(\\\\[\\\\\"'()bfnrt]|[^\\\\\"])*\"");
		private static final Pattern PATTERN_3 = Pattern.compile("(//|#).*$", Pattern.MULTILINE);
		private static final StyleContext STYLE_CONTEXT = StyleContext.getDefaultStyleContext();
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private final AttributeSet attributeSet1 = doCreateAttributeSet1();
		private final AttributeSet attributeSet2 = doCreateAttributeSet2();
		private final AttributeSet attributeSet3 = doCreateAttributeSet3();
		private final AttributeSet attributeSet4 = doCreateAttributeSet4();
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public JJavaTextPane() {
			addCaretListener(new CaretListenerImpl());
			setMargin(new Insets(10, 10, 10, 10));
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static AttributeSet doCreateAttributeSet1() {
			final AttributeSet attributeSet0 = STYLE_CONTEXT.addAttribute(STYLE_CONTEXT.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
			final AttributeSet attributeSet1 = STYLE_CONTEXT.addAttribute(attributeSet0, StyleConstants.FontFamily, Font.MONOSPACED);
			final AttributeSet attributeSet2 = STYLE_CONTEXT.addAttribute(attributeSet1, StyleConstants.FontSize, Integer.valueOf(16));
			
			return attributeSet2;
		}
		
		private static AttributeSet doCreateAttributeSet2() {
			final AttributeSet attributeSet0 = STYLE_CONTEXT.addAttribute(STYLE_CONTEXT.getEmptySet(), StyleConstants.Foreground, new Color(127, 0, 85));
			final AttributeSet attributeSet1 = STYLE_CONTEXT.addAttribute(attributeSet0, StyleConstants.FontFamily, Font.MONOSPACED);
			final AttributeSet attributeSet2 = STYLE_CONTEXT.addAttribute(attributeSet1, StyleConstants.FontSize, Integer.valueOf(16));
			final AttributeSet attributeSet3 = STYLE_CONTEXT.addAttribute(attributeSet2, StyleConstants.Bold, Boolean.TRUE);
			
			return attributeSet3;
		}
		
		private static AttributeSet doCreateAttributeSet3() {
			final AttributeSet attributeSet0 = STYLE_CONTEXT.addAttribute(STYLE_CONTEXT.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
			final AttributeSet attributeSet1 = STYLE_CONTEXT.addAttribute(attributeSet0, StyleConstants.FontFamily, Font.MONOSPACED);
			final AttributeSet attributeSet2 = STYLE_CONTEXT.addAttribute(attributeSet1, StyleConstants.FontSize, Integer.valueOf(16));
			
			return attributeSet2;
		}
		
		private static AttributeSet doCreateAttributeSet4() {
			final AttributeSet attributeSet0 = STYLE_CONTEXT.addAttribute(STYLE_CONTEXT.getEmptySet(), StyleConstants.Foreground, new Color(63, 127, 95));
			final AttributeSet attributeSet1 = STYLE_CONTEXT.addAttribute(attributeSet0, StyleConstants.FontFamily, Font.MONOSPACED);
			final AttributeSet attributeSet2 = STYLE_CONTEXT.addAttribute(attributeSet1, StyleConstants.FontSize, Integer.valueOf(16));
			
			return attributeSet2;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private final class CaretListenerImpl implements CaretListener {
			public CaretListenerImpl() {
				
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
			@Override
			public void caretUpdate(final CaretEvent e) {
				final
				SwingWorker<Void, Void> swingWorker = new SwingWorkerImpl();
				swingWorker.execute();
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private final class SwingWorkerImpl extends SwingWorker<Void, Void> {
			public SwingWorkerImpl() {
				
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
			@Override
			protected Void doInBackground() {
				try {
					doMatch(JJavaTextPane.this.getDocument().getText(0, JJavaTextPane.this.getDocument().getLength()));
				} catch(final BadLocationException e) {
//					Do nothing.
				}
				
				return null;
			}
			
			@Override
			protected void done() {
//				Do nothing.
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
			private void doMatch(final String string){
				doResetStyledDocument();
				doMatchPattern1(string);
				doMatchPattern2(string);
				doMatchPattern3(string);
			}
			
			@SuppressWarnings("synthetic-access")
			private void doMatchPattern1(final String string) {
				final Matcher matcher = PATTERN_1.matcher(string);
				
				while(matcher.find()) {
					JJavaTextPane.this.getStyledDocument().setCharacterAttributes(matcher.start(1), matcher.end(1) - matcher.start(1), JJavaTextPane.this.attributeSet2, true);
				}
			}
			
			@SuppressWarnings("synthetic-access")
			private void doMatchPattern2(final String string) {
				final Matcher matcher = PATTERN_2.matcher(string);
				
				while(matcher.find()) {
					JJavaTextPane.this.getStyledDocument().setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), JJavaTextPane.this.attributeSet3, true);
				}
			}
			
			@SuppressWarnings("synthetic-access")
			private void doMatchPattern3(final String string) {
				final Matcher matcher = PATTERN_3.matcher(string);
				
				while(matcher.find()) {
					JJavaTextPane.this.getStyledDocument().setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), JJavaTextPane.this.attributeSet4, true);
				}
			}
			
			@SuppressWarnings("synthetic-access")
			private void doResetStyledDocument() {
				JJavaTextPane.this.getStyledDocument().setCharacterAttributes(0, JJavaTextPane.this.getDocument().getLength(), JJavaTextPane.this.attributeSet1, true);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class JTextAreaOutputStreamDecorator extends OutputStream {
		private final JTextArea jTextArea;
		private final OutputStream outputStream;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public JTextAreaOutputStreamDecorator(final JTextArea jTextArea, final OutputStream outputStream) {
			this.jTextArea = Objects.requireNonNull(jTextArea, "jTextArea == null");
			this.outputStream = Objects.requireNonNull(outputStream, "outputStream == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public void write(final int b) throws IOException {
			this.outputStream.write(b);
			
			final byte[] bytes = new byte[] {(byte)(b)};
			
			final String string = new String(bytes, "ISO-8859-1");
			
			final Runnable runnable = () -> this.jTextArea.append(string);
			
			SwingUtilities.invokeLater(runnable);
		}
	}
}
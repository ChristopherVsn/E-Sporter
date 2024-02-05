package composant.header;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import composant.buttons.PicturedButton;
import ressources.Pages;
import vue.VueMain;

public class HeaderActions implements ActionListener {

	private Header header;

	public HeaderActions(Header head) {
		this.header = head;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PicturedButton p = (PicturedButton) e.getSource();
		VueMain vue = (VueMain) this.header.getParent();
		Frame frame = this.header.getFrame();

		switch (p.getActionCommand()) {
			case "disconnect":
				vue.changePage(Pages.IDENTIFICATION);
				break;
			case "accueil":
				vue.changePage(Pages.ACCUEIL);
				break;
			case "reduce":
				frame.setState(Frame.ICONIFIED);
				break;
			case "close":
				frame.dispose();
				break;
			case "fullScreen":
				this.header.setFullScreen();
				break;
			case "minScreen":
				this.header.setMinScreen();
				break;
		}
	}
}

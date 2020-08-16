package cn.shadow.vacation_diary.gui.manual.content;

import java.util.List;

import cn.shadow.vacation_diary.gui.GuiManualBase;
import cn.shadow.vacation_diary.gui.manual.IAdvancedContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class DimensionContent extends TextContent implements IAdvancedContent {
	Button button;
	
	public DimensionContent(String string, int textWidth) {
		super(string, textWidth);
	}
	
	public DimensionContent(List<String> strings, int textWidth) {
		super(strings, textWidth);
	}
	
	@Override
    public void renderBack(Minecraft mc, int x, int y, int mouseX, int mouseY) {
		super.renderBack(mc, x, y, mouseX, mouseY);
	}

	@Override
	public void onAdded(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
		// TODO Auto-generated method stub
        this.button = new Button(base.width / 2 - 40, 96, 80, 20, "Save", (button) -> {
        });
        base.addButtonToGUI(button);

	}

	@Override
	public void onRemoved(Minecraft mc, GuiManualBase base, int contentX, int contentY) {
		// TODO Auto-generated method stub
		this.button = null;
		base.removeButtonToGUI(button);
	}
}

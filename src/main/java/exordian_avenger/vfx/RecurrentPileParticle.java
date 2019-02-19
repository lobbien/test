package exordian_avenger.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import exordian_avenger.Exordian_avenger;

public class RecurrentPileParticle extends AbstractGameEffect {
	private float x;
	private float y;
	private float vX;
	private float scale = 0.01F;
	private float targetScale;
	private static TextureRegion img;

	public RecurrentPileParticle(float x, float y) {
		if (img == null) {
			img = Exordian_avenger.PARTICLESREGION;
		}
		this.targetScale = (MathUtils.random(0.5F, 0.7F) * Settings.scale);
		this.color = new Color();
		this.color.a = 0.0F;
		this.color.g = MathUtils.random(0.2F, 0.4F);
		this.color.r = (this.color.g + 0.1F);
		this.color.b = (this.color.r + 0.1F);

		this.x = (x - img.getRegionWidth() / 2.0F);
		this.y = (y - img.getRegionHeight() / 2.0F);
		this.rotation = MathUtils.random(360.0F);
		this.startingDuration = 1.0F;
		this.duration = this.startingDuration;
	}

	public void update() {
		this.scale = Interpolation.bounceIn.apply(this.targetScale, 0.1F, this.duration / this.startingDuration);
		this.rotation += this.vX * this.startingDuration * Gdx.graphics.getDeltaTime();
		this.color.a = (this.duration / this.startingDuration);
		this.x = (float) (this.x + Math.cos(this.rotation/360.0F * 2.0F * Math.PI)/3.0F);
		this.y = (float) (this.y + Math.sin(this.rotation/360.0F * 2.0F * Math.PI)/3.0F);
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) {
			this.isDone = true;
		}
	}

	public void render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(img, this.x, this.y, img.getRegionWidth() /2.0F, img.getRegionHeight() / 2.0F, img.getRegionWidth() * 4.0F,
				img.getRegionHeight() * 4.0F, this.scale, this.scale, this.rotation);
	}

	public void dispose() {
	}
}

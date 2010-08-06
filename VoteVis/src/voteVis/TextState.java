package voteVis;

import java.util.ArrayList;
import processing.core.*;

public class TextState extends TransitionState {
	String state_text_;
	processing.core.PGraphics state_graphics_;
	int max_text_width_;
	int line_space_ = 5; // in pixels
	PFont text_font_;
	int text_size_;

	// Use color(0, 0) for just text no background
	TextState(VoteVisApp p_, String state_text_, int bg_color, int state_dim_,
		PFont text_font_, int text_size_) {
		super(p_, state_dim_);

		max_text_width_ = state_dim_ - 20;

		this.text_font_ = text_font_;
		this.text_size_ = text_size_;

		TextDetails details = get_text_details(state_text_);
		int line_counter = 0;

		this.state_text_ = state_text_;
		// rendering with P3D does not look smooth hence JAVA2D
		state_graphics_ = p_.createGraphics(state_dim_, state_dim_, PApplet.JAVA2D);
		state_graphics_.beginDraw();
		state_graphics_.background(bg_color); // Assume additive blending for
												// now
		state_graphics_.noStroke();
		state_graphics_.fill(255);
		state_graphics_.textFont(text_font_, text_size_);
		state_graphics_.textAlign(PApplet.CENTER);
		state_graphics_.translate(state_dim_ / 2, state_dim_ / 2);
		state_graphics_.translate(0,
				-details.height() / 2 + details.line_height() / 2);

		for (int i = 0; i < details.lines().size(); ++i) {
			state_graphics_.text((String) details.lines().get(i), 0, 0);
			state_graphics_.translate(0, details.line_height() + line_space_);
		}

		state_graphics_.endDraw();
	}

	PImage get_image() {
		return state_graphics_;
	}

	// method with help of Gleb
	private TextDetails get_text_details(String message) {
		p_.textFont(text_font_, text_size_);
		float line_height = p_.textAscent() + p_.textDescent();
		TextDetails details;

		if (p_.textWidth(message) < max_text_width_) {
			ArrayList<String> lines = new ArrayList<String>();
			lines.add(message);
			float w = p_.textWidth(message);
			float h = line_height;
			details = new TextDetails(w, h, lines, line_height);
			return details;
		}

		// OK chop it up
		float one_space = p_.textWidth(" ");
		String[] message_split = message.split(" ");
		float line_width = 0;
		String current_line = "";
		ArrayList<String> lines = new ArrayList<String>();

		for (int i = 0; i < message_split.length; ++i) {
			current_line += message_split[i];
			line_width += p_.textWidth(message_split[i]);

			if (line_width > max_text_width_ ||
			// Check if the next word is going to go over
					(i + 1 < message_split.length && (line_width
							+ p_.textWidth(message_split[i + 1]) + one_space > max_text_width_))) {
				line_width = 0;
				lines.add(new String(current_line));
				current_line = "";
				continue;
			}

			if (i == (message_split.length - 1)) {
				lines.add(new String(current_line));
				break;
			}

			current_line += " ";
			line_width += one_space;
		}

		float text_width = 0;
		// find max width
		for (int i = 0; i < lines.size(); ++i) {
			String l = (String) lines.get(i);
			float w = p_.textWidth(l);
			if (w > text_width)
				text_width = w;
		}

		float text_height = lines.size() * line_height + (lines.size() - 1)
				* line_space_;

		details = new TextDetails(text_width, text_height, lines, line_height);
		return details;
	}

	private class TextDetails {
		float width_;
		float height_;
		ArrayList<String> lines_;
		float line_height_;

		TextDetails(float width_, float height_, ArrayList<String> lines_,
				float line_height_) {
			this.width_ = width_;
			this.height_ = height_;
			this.lines_ = lines_;
			this.line_height_ = line_height_;
		}

		ArrayList<String> lines() {
			return lines_;
		}

		float width() {
			return width_;
		}

		float height() {
			return height_;
		}

		float line_height() {
			return line_height_;
		}
	}
}
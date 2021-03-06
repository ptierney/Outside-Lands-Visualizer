
abstract class TransitionState {
  TransitionState() {
    // Nothing here
  }

  abstract PImage get_image();
}

class CompositeState extends TransitionState {
  PhotoState photo_state_;
  TextState text_state_;
  PGraphics composite_graphics_;

  CompositeState(PhotoState photo_state_, TextState text_state_) {
    this.photo_state_ = photo_state_;
    this.text_state_ = text_state_;

    composite_graphics_ = createGraphics(unit_dim, unit_dim, JAVA2D);
    composite_graphics_.beginDraw();
    composite_graphics_.image(photo_state_.get_image(), 0, 0);
    composite_graphics_.image(text_state_.get_image(), 0, 0);
    composite_graphics_.endDraw();
  }

  PImage get_image() {
    return composite_graphics_;
  }
}

class PhotoState extends TransitionState {
  PImage state_image_;

  PhotoState(PImage photo) {
    // Assert correct image
    if (photo.width != unit_dim || photo.height != unit_dim)
      exit();

    state_image_ = photo;
  }

  PImage get_image() {
    return state_image_;
  }
}

class TextState extends TransitionState {
  String state_text_;
  PGraphics state_graphics_;
  int max_text_width_;
  int line_space_ = 5; // in px
  int state_dim_ = unit_dim;

  // Use color(0, 0) for just text no background
  TextState(String state_text_, color bg_color) {
    max_text_width_ = unit_dim - 20;

    TextDetails details = get_text_details(state_text_);
    int line_counter = 0;

    this.state_text_ = state_text_;
    // rendering with P3D does not look smooth hence JAVA2D
    state_graphics_ = createGraphics(unit_dim, unit_dim, JAVA2D);
    state_graphics_.beginDraw();
    state_graphics_.background(bg_color); // Assume additive blending for now
    state_graphics_.noStroke();
    state_graphics_.fill(255);
    state_graphics_.textFont(text_font, text_size);
    state_graphics_.textAlign(CENTER); 
    state_graphics_.translate(state_dim_ / 2, state_dim_ / 2);
    state_graphics_.translate(0, -details.height() / 2 + details.line_height() / 2);
    
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
  TextDetails get_text_details(String message) {
    float line_height = textAscent()+textDescent();
    TextDetails details;

    if (textWidth(message) < max_text_width_) {
      ArrayList lines = new ArrayList();
      lines.add(message);
      float w = textWidth(message);
      float h = line_height;
      details = new TextDetails(w, h, lines, line_height);
      return details;
    }

    // OK chop it up
    float one_space = textWidth(" ");
    String[] message_split = message.split(" ");
    float line_width = 0;
    String current_line = "";
    ArrayList lines = new ArrayList();
    
    for(int i = 0; i < message_split.length; ++i) {
      current_line += message_split[i];
      line_width += textWidth(message_split[i]);
      
      if(line_width > max_text_width_ || 
        // Check if the next word is going to go over
        (i + 1 < message_split.length && (line_width + textWidth(message_split[i + 1]) + one_space > max_text_width_)) )  {
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
      float w = textWidth(l);
      if (w > text_width)
        text_width = w;
    }
    
    float text_height = lines.size() * line_height + (lines.size() - 1) * line_space_;
    
    details = new TextDetails(text_width, text_height, lines, line_height);
    return details;
  }
}

class TextDetails {
  float width_;
  float height_;
  ArrayList lines_;
  float line_height_;

  TextDetails(float width_, float height_, ArrayList lines_, float line_height_) {
    this.width_ = width_;
    this.height_ = height_;
    this.lines_ = lines_;
    this.line_height_ = line_height_;
  }

  ArrayList lines() {
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


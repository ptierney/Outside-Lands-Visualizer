
class BoxFactory {

  BoxFactory() {
    // nothing here
  }

  Box create_box() {
    int ran_index = get_lowest_column();

    int new_x = ran_index * (unit_dim + box_gap) + box_gap + unit_dim / 2;
    int new_y = box_gap + unit_dim / 2;

    VoteBox b = new VoteBox(new_x, new_y);
    b.set_information(int(random(5)), int(random(5)), int(random(5)), 
    int(random(5)));

    return b;
  }

  int get_lowest_column() {
    int curr_column = 0;
    int lowest_column = 0;
    int lowest_height = 0;
    boolean column_break = false;
    // walk along the columns
    for (int i = h_unit_dim + box_gap; i < width; i += unit_dim + box_gap) {
      column_break = false;
      for (int j = h_unit_dim + box_gap; j < height && column_break == false; j += unit_dim + box_gap) {
        for (int k = 0; k < vote_boxes.size() && column_break == false; k++) {
          Box b = (Box) vote_boxes.get(k);

          if (b.falling() == false && b.is_inside(i, j)) {
            if (b.y() > lowest_height) {
              lowest_column = curr_column;
              lowest_height = int(b.y());
            }
            // Jump to next column
            column_break = true;
          }
        }
      }
      if (column_break == false) { // Then we did not find a box is this column
        lowest_column = curr_column;
        lowest_height = height;
      }

      ++curr_column;
    }

    return lowest_column;
  }
}


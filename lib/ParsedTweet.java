package twitpush;

import java.io.File;

public class ParsedTweet {
	
	public String getId() {
		return id;
	}
	public String getProfile_image_url() {
		return profile_image_url;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getFrom_user() {
		return from_user;
	}
	public String getFrom_user_id() {
		return from_user_id;
	}
	public String getTo_user() {
		return to_user;
	}
	public String getTo_user_id() {
		return to_user_id;
	}
	public String getGeo() {
		return geo;
	}
	public String getIso_language_code() {
		return iso_language_code;
	}
	public String getText() {
		return text;
	}
	public String getSource() {
		return source;
	}
	public String getCategory() {
		return category;
	}
	public File getUserimage120() {
		return userimage120;
	}
	public File getUserimage220() {
		return userimage220;
	}
	
	
	//original tweet data
	public String id;
	private String profile_image_url;
	private String created_at;
	private String from_user;
	private String from_user_id;
	private String to_user;
	private String to_user_id;	
	private String geo;
	private String iso_language_code;
	private String text;
	private String source;
	
	//added data from parsing
	private String category;
	private File userimage120;
	private File userimage220;
	
}

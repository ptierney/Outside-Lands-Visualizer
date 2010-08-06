package twitpush;

public class TweetFeed {

	private String mryouthserver = "url";
	public void startPolling() {
		
	}
	
	public void stopPolling(){
		
	}
	
	public ParsedTweet[] getTweets(String category, int count){
		return new ParsedTweet[0];
	}
	
	public ParsedTweet[] getMusicTweets(int count){
		return  getTweets("music", count);
	}
	
	public ParsedTweet[] getFoodTweets(int count){
		return  getTweets("food", count);
	}
	
	public ParsedTweet[] getWineTweets(int count){
		return  getTweets("wine", count);
	}
	
	public ParsedTweet[] getEcoTweets(int count){
		return  getTweets("eco", count);
	}
	public ParsedTweet[] getArtTweets(int count){
		return  getTweets("art", count);
	}
	
}

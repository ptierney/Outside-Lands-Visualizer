
#include <vector>

#include <cinder/app/AppBasic.h>

class BoxFallApp : public ci::app::AppBasic {
public:
    void prepareSettings(Settings* settings);

    void draw();
};

void BoxFallApp::prepareSettings(Settings* settings )
{
	settings->setFullScreen(false);
    settings->setShouldQuit(false);
}

void BoxFallApp::draw() {
	ci::gl::setMatricesWindow(getWindowSize());
	// this pair of lines is the standard way to clear the screen in OpenGL
    ci::gl::clear(ci::Color(0.1f, 0.1f, 0.1f ));


}

// This line tells Cinder to actually create the application
CINDER_APP_BASIC(BoxFallApp, ci::app::RendererGl)
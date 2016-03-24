package twiscode.masakuuser.Model;

/**
 * Created by Unity on 18/05/2015.
 */
public class ModelNavDrawer {
    private boolean showNotify;
    private String title;

    public ModelNavDrawer() {

    }

    public ModelNavDrawer(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package track.container.config;

import java.util.List;

public class Root {
    private List<Bean> beans;

    public Root() {}

    public Root(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }
}

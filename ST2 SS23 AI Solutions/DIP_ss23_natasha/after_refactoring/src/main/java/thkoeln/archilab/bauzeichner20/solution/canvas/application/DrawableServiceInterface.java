package thkoeln.archilab.bauzeichner20.solution.canvas.application;

import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Drawable;

public interface DrawableServiceInterface {
    public void save( Drawable drawable );

    public Drawable createDefaultDoor();
}

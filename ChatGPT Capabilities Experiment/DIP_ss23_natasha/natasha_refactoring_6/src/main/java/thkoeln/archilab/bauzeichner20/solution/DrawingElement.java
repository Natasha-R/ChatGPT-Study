package thkoeln.archilab.bauzeichner20.solution;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static thkoeln.archilab.bauzeichner20.solution.DirectionType.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawingElement implements IDrawingElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public Integer x_bottom_left;
    public Integer y_bottom_left;

    public Integer width;
    public Integer height;

    private DrawingElementType drawingElementType;

    private String doorMaterial = "wood";
    private Integer windowNumberOfGlazing = 2;

    @ManyToOne
    private ICanvas canvas;

    public static DrawingElement door( Integer x, Integer y, Integer w, Integer h ) {
        DrawingElement door = new DrawingElement();
        door.setX_bottom_left( x );
        door.setY_bottom_left( y );
        door.setWidth( w );
        door.setHeight( h );
        door.setDrawingElementType( DrawingElementType.DOOR );
        return door;
    }

    public static DrawingElement window( Integer x, Integer y, Integer w, Integer h ) {
        DrawingElement window = new DrawingElement();
        window.setX_bottom_left( x );
        window.setY_bottom_left( y );
        window.setWidth( w );
        window.setHeight( h );
        window.setDrawingElementType( DrawingElementType.WINDOW );
        return window;
    }

    @Override
    public void move( DirectionType direction, Integer distance ) {
        if ( direction == null || distance == null || distance < 0 ) {
            throw new BauzeichnerException( "Direction and distance must not be null and distance must be positive" );
        } else {
            switch (direction) {
                case TOP:
                    y_bottom_left += distance;
                    break;
                case BOTTOM:
                    y_bottom_left -= distance;
                    break;
                case LEFT:
                    x_bottom_left -= distance;
                    break;
                case RIGHT:
                    x_bottom_left += distance;
                    break;
            }
            if ( x_bottom_left < 20 || y_bottom_left < 20 ) {
                throw new BauzeichnerException( "DrawingElement must not be moved outside the canvas (20cm buffer)" );
            } else {
                if ( x_bottom_left + width > canvas.getWidth() - 20 || y_bottom_left + height > canvas.getHeight() - 20 ) {
                    throw new BauzeichnerException( "DrawingElement must not be moved outside the canvas (20cm buffer)" );
                } else {
                    if ( drawingElementType == DrawingElementType.DOOR ) {
                        if ( x_bottom_left < 100 ) {
                            throw new BauzeichnerException( "Door must be 1m away from left canvas edge" );
                        }
                        if ( x_bottom_left + width > canvas.getWidth() + 100 ) {
                            throw new BauzeichnerException( "Door must be 1m away from right canvas edge" );
                        }
                    } else {
                        List<IDrawingElement> neighbours = canvas.getNeighboursOf( this );
                        for ( IDrawingElement neighbour : neighbours ) {
                            if ( neighbour.getX_bottom_left() + neighbour.getWidth() > x_bottom_left - 10 ) {
                                throw new BauzeichnerException( "Left neighbour too close" );
                            } else {
                                if ( x_bottom_left + width > neighbour.getX_bottom_left() - 10 ) {
                                    throw new BauzeichnerException( "Right neighbour too close" );
                                } else {
                                    if ( neighbour.getY_bottom_left() + neighbour.getHeight() > y_bottom_left - 10 ) {
                                        throw new BauzeichnerException( "Bottom neighbour too close" );
                                    } else {
                                        if ( y_bottom_left + height > y_bottom_left - 10 ) {
                                            throw new BauzeichnerException( "Top neighbour too close" );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Returns the position of the edge of the DrawingElement in the given direction.
     *
     * @param direction
     * @return position of the edge
     */
    @Override
    public Integer edgePositionAt( DirectionType direction ) {
        switch (direction) {
            case TOP:
                return y_bottom_left + height;
            case BOTTOM:
                return y_bottom_left;
            case LEFT:
                return x_bottom_left;
            case RIGHT:
                return x_bottom_left + width;
            default:
                throw new BauzeichnerException( "Direction must not be null" );
        }
    }


    /**
     * Returns the distance to the given DrawingElement in the vertical direction.
     *
     * @param drawingElement
     * @return distance, or -1 if they overlap
     */
    @Override
    public Integer verticalDistanceTo( IDrawingElement drawingElement ) {
        if ( drawingElement == null ) throw new BauzeichnerException( "DrawingElement must not be null" );

        if ( edgePositionAt( BOTTOM ) < drawingElement.edgePositionAt( TOP ) &&
                edgePositionAt( TOP ) >= drawingElement.edgePositionAt( TOP ) ) return -1;
        if ( edgePositionAt( TOP ) > drawingElement.edgePositionAt( BOTTOM ) &&
                edgePositionAt( BOTTOM ) <= drawingElement.edgePositionAt( BOTTOM ) ) return -1;
        Integer bottomDistance = edgePositionAt( BOTTOM ) - drawingElement.edgePositionAt( TOP );
        Integer topDistance = drawingElement.edgePositionAt( BOTTOM ) - edgePositionAt( TOP );
        Integer distance = Math.max( topDistance, bottomDistance );
        return distance;
    }


    /**
     * Returns the distance to the given DrawingElement in the horizontal direction.
     *
     * @param drawingElement
     * @return distance, or -1 if they overlap
     */
    @Override
    public Integer horizontalDistanceTo( IDrawingElement drawingElement ) {
        if ( drawingElement == null ) throw new BauzeichnerException( "DrawingElement must not be null" );
        if ( edgePositionAt( LEFT ) < drawingElement.edgePositionAt( RIGHT ) &&
                edgePositionAt( RIGHT ) >= drawingElement.edgePositionAt( RIGHT ) ) return -1;
        if ( edgePositionAt( RIGHT ) > drawingElement.edgePositionAt( LEFT ) &&
                edgePositionAt( LEFT ) <= drawingElement.edgePositionAt( LEFT ) ) return -1;
        Integer leftDistance = edgePositionAt( LEFT ) - drawingElement.edgePositionAt( RIGHT );
        Integer rightDistance = drawingElement.edgePositionAt( LEFT ) - edgePositionAt( RIGHT );
        Integer distance = Math.max( leftDistance, rightDistance );
        return distance;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof DrawingElement ) ) return false;
        DrawingElement that = (DrawingElement) o;
        return id.equals( that.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

    public Integer getX_bottom_left() {
        return x_bottom_left;
    }

    public Integer getY_bottom_left() {
        return y_bottom_left;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setX_bottom_left(Integer x_bottom_left) {
        this.x_bottom_left = x_bottom_left;
    }

    public void setY_bottom_left(Integer y_bottom_left) {
        this.y_bottom_left = y_bottom_left;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setCanvas(ICanvas canvas) {
        this.canvas = canvas;
    }
}

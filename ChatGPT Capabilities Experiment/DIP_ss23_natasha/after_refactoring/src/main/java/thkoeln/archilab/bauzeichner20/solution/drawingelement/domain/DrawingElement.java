package thkoeln.archilab.bauzeichner20.solution.drawingelement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Canvas;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Drawable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType.*;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DrawingElement extends Drawable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    protected Integer xBottomLeft;
    protected Integer yBottomLeft;

    protected Integer width;
    protected Integer height;

    @ManyToOne
    protected Canvas canvas;

    public DrawingElement( Integer xBottomLeft, Integer yBottomLeft, Integer width, Integer height ) {
        setXBottomLeft( xBottomLeft );
        setYBottomLeft( yBottomLeft );
        setWidth( width );
        setHeight( height );
    }

    public void move( DirectionType direction, Integer distance ) {
        if ( direction == null || distance == null || distance < 0 )
            throw new BauzeichnerException( "Direction and distance must not be null and distance must be positive" );
        changeDistance( direction, distance );
        if ( xBottomLeft < 20 || yBottomLeft < 0 )
            throw new BauzeichnerException( "DrawingElement must not be moved outside the canvas (20cm buffer)" );
        if ( canvas != null &&
                (xBottomLeft + width > canvas.getWidth() - 20 || yBottomLeft + height > canvas.getHeight() - 20) )
            throw new BauzeichnerException( "DrawingElement must not be moved outside the canvas (20cm buffer)" );
        checkForCollisions();
    }

    protected void checkForCollisions() {
        if ( canvas == null ) return;
        List<Drawable> neighbours = canvas.getNeighboursOf( this );
        for ( Drawable neighbour : neighbours ) {
            if ( neighbour.xBottomLeft() + neighbour.width() > xBottomLeft() - 10 )
                throw new BauzeichnerException( "Left neighbour too close" );
            if ( xBottomLeft() + width > neighbour.xBottomLeft() - 10 )
                throw new BauzeichnerException( "Right neighbour too close" );
            if ( neighbour.yBottomLeft() + neighbour.height() > yBottomLeft() - 10 )
                throw new BauzeichnerException( "Bottom neighbour too close" );
            if ( yBottomLeft() + height > yBottomLeft() - 10 ) {
                throw new BauzeichnerException( "Top neighbour too close" );
            }
        }
    }

    protected void changeDistance( DirectionType direction, Integer distance ) {
        switch (direction) {
            case TOP:
                yBottomLeft += distance;
                break;
            case BOTTOM:
                yBottomLeft -= distance;
                break;
            case LEFT:
                xBottomLeft -= distance;
                break;
            case RIGHT:
                xBottomLeft += distance;
                break;
        }
    }


    /**
     * Returns the position of the edge of the DrawingElement in the given direction.
     *
     * @param direction
     * @return position of the edge
     */
    public Integer edgePositionAt( DirectionType direction ) {
        switch (direction) {
            case TOP:
                return yBottomLeft + height;
            case BOTTOM:
                return yBottomLeft;
            case LEFT:
                return xBottomLeft;
            case RIGHT:
                return xBottomLeft + width;
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
    public Integer verticalDistanceTo( DrawingElement drawingElement ) {
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
    public Integer horizontalDistanceTo( DrawingElement drawingElement ) {
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
    public Integer xBottomLeft() {
        return xBottomLeft;
    }

    @Override
    public Integer yBottomLeft() {
        return yBottomLeft;
    }

    @Override
    public Integer width() {
        return width;
    }

    @Override
    public Integer height() {
        return height;
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
}

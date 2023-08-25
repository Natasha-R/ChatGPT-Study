package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;

@Service
public class CanvasFactory {
    public ICanvas create(Integer width, Integer height) {
        return new Canvas(width, height);
    }
}

package ms.montecarlo.model;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawerTask extends Task {

    private GraphicsContext gc;
    private int n;

    public DrawerTask(){

    }

    public DrawerTask(GraphicsContext graphic_context, int num_of_shots){
        this.gc = graphic_context;
        this.n = num_of_shots;
    }

    @Override
    protected Object call() throws Exception {
        return drawShapes();
    }

    private int drawShapes(){
        Random random = new Random();
        int argb, in = 0;
        double x, y, lx, ly, w, h;
        lx = gc.getCanvas().getLayoutX();
        ly = gc.getCanvas().getLayoutY();
        w = gc.getCanvas().getWidth();
        h = gc.getCanvas().getHeight();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,w,h);

        BufferedImage bi = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i<n; i++) {
            x = -8. + 16. * random.nextDouble();
            y = -8. + 16. * random.nextDouble();
            if(Equation.calc(x,y)){
                argb = 0xFFFFCC00;
                in++;
            }else{
                argb = 0xFF003366;
            }
            //(int)(((D-C)*(x-A)/(B-A)+C))
            bi.setRGB((int)(((w-0)*(x+8)/16+0)), (int)(h-((h-0)*(y+8)/16+0)), argb);
            if (i % 1000 == 0) {
                gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
            }
            updateProgress(i, n);
            if(isCancelled()) break;
        }
        return in;
    }
}

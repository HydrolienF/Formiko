public class designPanel extends JPanel{

  public double zoomFactor = 1;
  public boolean zoomer = true;
  public AffineTransform at;


  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      if (zoomer == true) {
          at = g2.getTransform();
          at.scale(zoomFactor, zoomFactor);
          zoomer = false;
      }
      g2.transform(at);
  }

  public void setZoomFactor(double factor){

      if(factor<this.zoomFactor){
          this.zoomFactor=this.zoomFactor/1.1;
      }
      else{
          this.zoomFactor=factor;
      }
      this.zoomer=true;
  }


  public double getZoomFactor() {
      return zoomFactor;
  }
}

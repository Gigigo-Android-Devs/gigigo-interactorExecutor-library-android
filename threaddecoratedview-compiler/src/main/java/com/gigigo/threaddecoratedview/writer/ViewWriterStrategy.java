package com.gigigo.threaddecoratedview.writer;

import com.gigigo.threaddecoratedview.model.EnclosingView;
import java.io.IOException;
import javax.annotation.processing.Filer;


public interface ViewWriterStrategy {
  void writeView(Filer filer, EnclosingView view) throws IOException;
}

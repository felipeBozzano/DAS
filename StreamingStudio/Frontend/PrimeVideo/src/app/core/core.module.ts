import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { LoaderComponent } from './layouts/loader/loader.component';
import { MessageDialogComponent } from './layouts/message-dialog/message-dialog.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    LoaderComponent,
    MessageDialogComponent
  ],
    imports: [
        CommonModule,
        NgbModalModule,
        MatProgressSpinnerModule,
    ],
  entryComponents: [
    MessageDialogComponent
  ],
  exports: [
    LoaderComponent
  ]
})
export class CoreModule { }

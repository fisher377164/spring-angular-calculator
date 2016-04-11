var gulp = require('gulp');
var browserSync = require('browser-sync').create();
var notify = require('gulp-notify');
var plumber = require('gulp-plumber');

gulp.task('serve', function () {
    browserSync.init({
        server: './src/main/webapp',
        port: 8080
    });

    browserSync.watch('./src/main/webapp/**/*.*').on('change', browserSync.reload);
});

gulp.task('default', gulp.series(
 
    gulp.parallel(
        'serve'
    )
));

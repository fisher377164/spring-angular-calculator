var gulp = require('gulp');
var browserSync = require('browser-sync').create();
var notify = require('gulp-notify');
var plumber = require('gulp-plumber');
// var rename = require('gulp-rename');
// var concat = require('gulp-concat');
// var uglify = require('gulp-uglify');

// gulp.task('scripts', function() {
//     gulp.src(['./lib/file3.js', './lib/file1.js', './lib/file2.js'])
//         .pipe(concat('all.js'))
//         .pipe(uglify())
//         .pipe(gulp.dest('./dist/'))
// });


// gulp.task('watch', function () {
//     gulp.watch('./src/main/webapp/**/*.js', gulp.series('scripts'));
//
// });


gulp.task('serve', function () {
    browserSync.init({
        server: './src/main/webapp',
        port: 8080
    });

    browserSync.watch('./src/main/webapp/**/*.*').on('change', browserSync.reload);
});

gulp.task('default', gulp.series(
    // 'scripts',
 
    gulp.parallel(
        // 'watch',
        'serve'
    )
));

var gulp = require('gulp');
var ts = require('gulp-typescript');
// var resolver = require('stylus').resolver;
var browserSync = require('browser-sync').create();
var notify = require('gulp-notify');
var plumber = require('gulp-plumber');
// var stylus = require('gulp-stylus');
var jade = require('gulp-jade');
var rename = require('gulp-rename');


gulp.task('ts', function () {
    return gulp.src('src/**/*.ts')
        .pipe(plumber({
            errorHandler: notify.onError(function (err) {
                return {
                    message: err.message
                };
            })
        }))
        .pipe(ts({
            noImplicitAny: true,
            out: 'output.js'
        }))
        .pipe(gulp.dest('src/main/webapp/built'));
});

gulp.task('watch', function () {
    gulp.watch('./src/main/webapp/**/*.ts', gulp.series('ts'));
    gulp.watch('./src/main/webapp/**/*.jade', gulp.series('jade'));
});

// Compile jade to html
gulp.task('jade', function () {
    return gulp.src('src/main/webapp/app/app.jade')
        .pipe(plumber({
            errorHandler: notify.onError(function (err) {
                return {
                    message: err.message
                };
            })
        }))
        .pipe(jade({
            pretty: true
        }))
        .pipe(rename('index.html'))
        .pipe(gulp.dest('./src/main/webapp/built'));
});

gulp.task('serve', function () {
    browserSync.init({
        server: './src/main/webapp/built',
        port: 8080
    });

    browserSync.watch('./src/main/webapp/built/**/*.*').on('change', browserSync.reload);
});

gulp.task('default', gulp.series(
    'ts',
    'jade',
    gulp.parallel(
        'watch',
        'serve'
    )
));

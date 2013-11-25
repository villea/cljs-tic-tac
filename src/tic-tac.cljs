(ns tic-tac)


(defn init-canvas [canvas]
	(doto canvas 
		(aset "width" (.-clientWidth canvas))
		(aset "height" (.-clientHeight canvas))))

(def canvas (init-canvas (.getElementById js/document "board")))

(def height (.-clientHeight canvas))
(def width (.-clientWidth canvas))
(def cell-height (/ height 10))
(def cell-width (/ width 10))

(def context (.getContext canvas "2d"))

(def moves (atom {}))

(def current-turn (atom \X))

(defn change-turn [c]
	(if (= c \X)
		\O
		\X))

(defn draw-rect [context color x y w h] 
   (doto context
	  (aset "fillStyle" color)
	  (.fillRect x y w h)))

(defn draw-line [context color x1 y1 x2 y2]
	(doto context
		(aset "strokeStyle" color)
		(.beginPath)
		(.moveTo x1 y1)
		(.lineTo x2 y2)
		(.stroke)))

;FIXME 
(let [w cell-width
	  h cell-height]
(doseq [i (range w width w)]
	(draw-line context "red" i 0 i height))
(doseq [i (range h height h)]
	(draw-line context "red" 0 i width i)))


(defn mouse-pos [canvas event]
	(let [rect (.getBoundingClientRect canvas)]
		  [(- (.-clientX event) (.-left rect))
		   (- (.-clientY event) (.-top rect))]))

(defn coord-to-cell [xy]
	[ (js/Math.floor (/ (get xy 0) cell-width))
	  (js/Math.floor (/ (get xy 1) cell-height)) ])

(defn paint-cell [color x y]
	(let [x-pos (* x cell-width)
		  y-pos (* y cell-height)]
    (draw-rect context "white" (+ x-pos 3) (+ y-pos 3) (- cell-width 3) (- cell-height 3))
	(aset context "fillStyle" "black")
	(aset context "textAlign" "center")
	(aset context "textBaseline" "middle")
	(aset context "font" "bold 20px sans-serif")
	(.fillText context color (+ x-pos (/ cell-width 2)) (+ y-pos (/ cell-height 2)))))

(.addEventListener canvas "mousedown" (fn [event]
    (let [[mx my] (coord-to-cell (mouse-pos canvas event))]
	  (paint-cell @current-turn mx my) 
	  (swap! moves assoc [mx my] @current-turn)
	  (swap! current-turn change-turn)
	  (.log js/console (str @moves)))))


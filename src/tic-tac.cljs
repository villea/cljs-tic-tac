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

(def moves (atom #{}))

(def current-color (atom (cycle ["red" "brown"])))

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

(defn paint-cell [x y]
	(draw-rect context "green" (* x cell-width) (* y cell-height) (- cell-width 1) (- cell-height 1)))

(.addEventListener canvas "click" (fn [event]
    (let [[mx my] (coord-to-cell (mouse-pos canvas event))]
	  (paint-cell mx my)
	  (swap! moves conj [mx my])
	  (.log js/console (str @current-color))
	  (.log js/console (str @moves)))))


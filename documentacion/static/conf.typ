// Variables
#let base-font-size = 13pt
#let text-font = "Newsreader Text"
#let heading-font = "Inter Display"
#let color-accent = blue

#let fs-400 = base-font-size
#let fs-500 = fs-400 * 1.2
#let fs-600 = fs-500 * 1.2
#let fs-700 = fs-600 * 1.2
#let fs-900 = fs-700 * 1.44

#let template(doc) = {
  // Page setting
  set page(
    paper: "us-letter",
    margin: (x: 1.3in, y: 1in),
    // numbering: "1",
  )
  set par(justify: true, leading: 0.9em)
  set text(fs-400, font: text-font)
  // Headings
  set heading(numbering: "1.1.", supplement: "Sección")
  show heading: it => {
    set text(fs-600, font: heading-font)
    set par(justify: false, leading: 0.4em)
    it.body
  }
  show heading.where(level: 1): it => {
    pagebreak(weak: true)
    set text(fs-900, font: heading-font, weight: 800)
    set par(justify: false, leading: 0.3em)
    set align(end)
    underline(extent: 0.25em, it.body)
    v(1em)
  }
  show heading.where(level: 2): it => {
    set text(fs-700, font: heading-font, weight: 700)
    set par(justify: false, leading: 0.4em)
    block(breakable: false)[
    #box(width: 1fr, repeat[ - ])
    #block(it.body)
    #v(0.5em)
    ]
  }
  // Table of Contents
  set outline(depth: 2, indent: auto)
  show outline.entry.where(level: 1): it => {
    set text(fs-500, font: heading-font, weight: 700)
    v(1em)
    it
  }
  // Miscellaneous
  set math.equation(numbering: "(1)")
  show figure.where(kind: table): set figure.caption(position: top)
  set figure(supplement: "Figura")

  show ref: set text(fill: color-accent)
  show cite: set text(fill: color-accent)
  show link: set text(fill: color-accent)
  
  show raw: it => {
    block(fill: luma(248), width: 100%, inset: 10pt, radius: 4pt)[
      #it
    ]
  } 
  
  // ----------------------------------------
  doc
}
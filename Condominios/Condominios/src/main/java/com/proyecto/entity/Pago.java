package com.proyecto.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob; // Importar para campos grandes (BLOB/CLOB)
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // Usamos Integer, como lo tenías. Si usas Long en otras entidades para IDs, considera la consistencia.

	@Column(nullable = false) // La fecha de pago es obligatoria
    private LocalDate fechaPago;

    @Column(nullable = false, unique = true) // El número de comprobante es obligatorio y debería ser único
    private String numeroComprobante; // Nuevo campo añadido

	@Column(nullable = true, length = 500) // Observaciones es opcional. Define un largo máximo si lo deseas.
    private String observaciones;

	@Column(nullable = false) // Estado no puede ser nulo
    private String estado; // PENDIENTE, PAGADO, VENCIDO (como String)

    @OneToOne
    @JoinColumn(name = "alicuota_id", unique = true) // Un pago está asociado a una única alícuota, y una alícuota solo puede tener un pago.
    private Alicuota alicuota;

    @Lob // Anotación para indicar que es un Large Object (BLOB)
    @Column(name = "comprobante_pdf", columnDefinition = "LONGBLOB") // Nombre de la columna en la DB
    private byte[] comprobantePdf; // Campo para almacenar el PDF como un array de bytes
}